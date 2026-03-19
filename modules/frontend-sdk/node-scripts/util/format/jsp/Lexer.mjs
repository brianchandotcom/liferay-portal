/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ReversibleMap from './ReversibleMap.mjs';
import permute from './permute.mjs';

/**
 * Class for creating lexer instances.
 *
 * Tokens are recognized by "matchers" created with `match('string')` or
 * `match(/regex/)`, and matchers can be combined through the use of various
 * combinators (`oneOf()`, `repeat()`, `sequence()` etc).
 *
 * Given a set of matchers, the principal operations are then:
 *
 * - peek(): Performs lookahead to see whether a particular matcher would match
 *   at the current location in the input.
 * - consume(): Recognizes a token and moves forward in the input. The
 *   `consume()` call must succeed or an error will be thrown (there is
 *   no rewind functionality for failed recognition; use `peek()` to
 *   perform lookahead instead).
 *
 * It is possible to do some context-sensitive lexing using a supplied `meta`
 * object. Arbitrary data can be written to the `meta` object (often this will
 * happen in an `onMatch()` callback once a matcher has succeeded) and read at
 * any time.
 */
class Lexer {
	constructor(callback) {

		/**
		 * Map of all matchers keyed by name.
		 */
		this._matchers = new Map();

		this._callback = callback;
	}

	*lex(input) {
		const lookup = (matcher) => this.lookup(matcher);

		const setMatcher = (name, matcher) => this._matchers.set(name, matcher);

		/**
		 * Arbitrary metadata passed to matchers' `onMatch()` callbacks.
		 */
		const meta = new ReversibleMap();

		/**
		 * A matcher that always matches and consumes nothing.
		 */
		const pass = {
			description: '«pass»',

			exec(_string) {
				return getMatchObject('');
			},
		};

		/**
		 * A matcher that never matches anything.
		 */
		const never = {
			description: '«never»',

			exec(_string) {
				return null;
			},
		};

		/**
		 * Returns a matcher that looks up another matcher by name and uses it.
		 */
		function a(matcherName) {
			return {
				get description() {
					return this._description || lookup(matcherName).description;
				},

				except,

				exec(string) {
					const matcher = lookup(matcherName);

					if (!matcher) {
						throw new Error(
							`Failed to find matcher with name ${name}`
						);
					}

					if (this._onEnter) {
						this._onEnter(meta);
					}

					const match = matcher.exec(string);

					if (match !== null) {
						if (this._onMatch) {
							this._onMatch(match, meta);
						}
					}

					return match;
				},

				name,

				onEnter,

				onMatch,

				to,

				until,
			};
		}

		/**
		 * Alias for `an()` so that you can write matchers that read like
		 * `an('ATTTRIBUTE')` instead of `a('ATTRIBUTE')`.
		 */
		const an = a;

		/**
		 * Returns a composite matcher that matches if all the passed `matchers` match,
		 * irrespective of order.
		 *
		 * This is an order-insensitive analog of `sequence()`.
		 *
		 * Note that permutating has O(!N) runtime, so should be used sparingly.
		 */
		function allOf(...matchers) {

			// Given matchers [a, b], permute them (eg. [[a, b], [b, a]])...

			const permutations = permute(matchers);

			// ...and transform into: oneOf(sequence(a, b), sequence(b, a)):

			const matcher = oneOf(...permutations.map((m) => sequence(...m)));

			return {
				get description() {
					return (
						this._description ||
						'allOf:(' +
							matchers
								.map((matcher) => lookup(matcher).description)
								.join(', ') +
							')'
					);
				},

				exec(string) {
					return matcher.exec(string);
				},

				name,
			};
		}

		/**
		 * Returns a matcher that modifies the parent matcher by having it
		 * return `null` whenever the `predicate` matcher identifies a match.
		 */
		function except(predicate) {
			const parent = this;

			return {
				get description() {
					return (
						this._description || `^${lookup(parent).description}`
					);
				},

				exec(string) {
					const match = parent.exec(string);

					if (match !== null) {
						const negated = lookup(predicate).exec(string);

						if (negated !== null) {
							return null;
						}

						return match;
					}

					return null;
				},

				name,

				to,

				until,
			};
		}

		/**
		 * Turns `stringOrRegExp` into a RegExp with additional properties
		 * (`description`, `onMatch`, `to` etc).
		 */
		function match(stringOrRegExp) {
			const pattern =
				typeof stringOrRegExp === 'string'
					? escape(stringOrRegExp)
					: stringOrRegExp.source;

			const matcher = new RegExp(`^(?:${pattern})`, 'u');

			Object.defineProperty(matcher, 'description', {
				get: () => {
					return (
						matcher._description ||
						(typeof stringOrRegExp === 'string'
							? JSON.stringify(stringOrRegExp)
							: stringOrRegExp.toString())
					);
				},
			});

			matcher.exec = (string) => {
				const match = RegExp.prototype.exec.call(matcher, string);

				if (match !== null) {
					if (matcher._onMatch) {
						matcher._onMatch(match, meta);
					}
				}

				return match;
			};

			matcher.name = name.bind(matcher);

			matcher.onEnter = onEnter.bind(matcher);

			matcher.onMatch = onMatch.bind(matcher);

			matcher.to = to.bind(matcher);

			matcher.until = until.bind(matcher);

			return matcher;
		}

		/**
		 * Returns a matcher that always matches. If the supplied `matcher` matches, we
		 * return the match, otherwise we return a zero-width match.
		 *
		 * Conceptually equivalent to the "?" regex special character.
		 */
		function maybe(matcher) {
			return {
				get description() {
					return (
						this._description || `${lookup(matcher).description}?`
					);
				},

				exec(string) {
					const match = lookup(matcher).exec(string);

					if (match !== null) {
						return match;
					}
					else {

						// Fake a zero-width match.

						return getMatchObject('');
					}
				},

				name,
			};
		}

		/**
		 * Assigns a name to a matcher.
		 *
		 * Once a matcher has a name, other matchers can reference it by name
		 * using the `a()` or `an()` functions.
		 */
		function name(string) {
			this._description = string;

			setMatcher(string, this);

			return this;
		}

		/**
		 * Registers a callback to be invoked when a matcher is entered
		 * (immediately prior to attempting to detect a match).
		 */
		function onEnter(callback) {
			this._onEnter = callback;

			return this;
		}

		/**
		 * Registers a callback to be invoked when a matcher matches.
		 */
		function onMatch(callback) {
			this._onMatch = callback;

			return this;
		}

		/**
		 * Returns a composite matcher that matches if one of the supplied matchers
		 * matches.
		 *
		 * Conceptually equivalent to the "|" regex special character.
		 */
		function oneOf(...matchers) {
			return {
				get description() {
					return (
						this._description ||
						matchers
							.map((matcher) => lookup(matcher).description)
							.join(' | ')
					);
				},

				except,

				exec(string) {
					if (this._onEnter) {
						this._onEnter(meta);
					}

					for (let i = 0; i < matchers.length; i++) {
						meta.checkpoint();

						const matcher = lookup(matchers[i]);

						const match = matcher.exec(string);

						if (match !== null) {
							if (this._onMatch) {
								this._onMatch(match, meta);
							}

							return match;
						}

						meta.rollback();
					}

					return null;
				},

				name,

				onEnter,

				onMatch,

				test,

				to,

				until,
			};
		}

		/**
		 * Returns a composite matcher that matches if the passed `matcher` matches at
		 * least once.
		 *
		 * Conceptually equivalent to the "+" regex special char.
		 */
		function repeat(matcher) {
			return {
				get description() {
					return (
						this._description || `${lookup(matcher).description}+`
					);
				},

				exec(string) {
					let remaining = string;
					let consumed = '';

					while (remaining !== '') {
						const match = lookup(matcher).exec(remaining);

						if (match !== null) {
							remaining = remaining.slice(match[0].length);

							consumed += match[0];
						}
						else {
							break;
						}
					}

					if (consumed) {
						return getMatchObject(consumed);
					}
					else {
						return null;
					}
				},

				name,
			};
		}

		/**
		 * Returns a composite matcher that matches if all of the supplied matchers
		 * match, in order.
		 */
		function sequence(...matchers) {
			return {
				get description() {
					return (
						this._description ||
						matchers
							.map((matcher) => lookup(matcher).description)
							.join(' ')
					);
				},

				exec(string) {
					meta.checkpoint();

					if (this._onEnter) {
						this._onEnter(meta);
					}

					let remaining = string;
					let matched = '';

					for (let i = 0; i < matchers.length; i++) {
						const matcher = lookup(matchers[i]);
						const match = matcher.exec(remaining);

						if (match !== null) {
							remaining = remaining.slice(match[0].length);

							matched += match[0];
						}
						else {
							meta.rollback();

							return null;
						}
					}

					const match = getMatchObject(matched);

					if (this._onMatch) {
						this._onMatch(match, meta);
					}

					return match;
				},

				name,

				onEnter,

				onMatch,

				test,
			};
		}

		function test(string) {
			return this.exec(string) !== null;
		}

		/**
		 * Returns a matcher that modifies the parent matcher by having it
		 * repeat 0 or more times up-to-and-including where the
		 * `predicate` matcher identifies a match.
		 *
		 * If the matcher reaches the end of the input without matching the
		 * predicate, that is not considered a match.
		 *
		 * See `until()` for a matcher that matches up-to-but-not-including its
		 * predicate.
		 */
		function to(predicate) {
			const parent = this;

			return {
				get description() {
					return (
						this._description ||
						`->> ${lookup(predicate).description}`
					);
				},

				exec(string) {
					let remaining = string;
					let consumed = '';

					while (remaining !== '') {
						let match = predicate.exec(remaining);

						if (match !== null) {
							remaining = remaining.slice(match[0].length);

							return getMatchObject(consumed + match[0]);
						}

						match = parent.exec(remaining);

						if (match !== null) {
							remaining = remaining.slice(match[0].length);

							consumed += match[0];
						}
						else {
							break;
						}
					}

					return null;
				},

				name,
			};
		}

		/**
		 * Returns a matcher that modifies the parent matcher by having it
		 * repeat 0 or more times up-to-but-not-including where the
		 * `predicate` matcher identifies a match.
		 *
		 * If the matcher reaches the end of the input without matching the
		 * predicate, that is considered a match.
		 *
		 * See `to()` for a matcher that matches up-to-and-including its
		 * predicate.
		 */
		function until(predicate) {
			const parent = this;

			return {
				get description() {
					return (
						this._description ||
						`-> ${lookup(predicate).description}`
					);
				},

				exec(string) {
					let remaining = string;
					let consumed = '';

					while (remaining !== '') {
						let match = predicate.exec(remaining);

						if (match !== null) {
							break;
						}

						match = parent.exec(remaining);

						if (match === null) {
							break;
						}
						else {
							remaining = remaining.slice(match[0].length);

							consumed += match[0];
						}
					}

					return getMatchObject(consumed);
				},

				name,
			};
		}

		/**
		 * Returns a matcher that evaluates the `predicate` and, if true,
		 * applies `matcher`, and otherwise applies the `alternate` matcher.
		 *
		 * If no explicit `alternate` is provided, uses the `pass` matcher which
		 * always matches with a 0-length match.
		 */
		function when(predicate, matcher, alternate = pass) {
			return {
				get description() {
					return (
						this._description ||
						`predicate(${lookup(matcher).description}, ${
							lookup(alternate).description
						})`
					);
				},

				exec(string) {
					if (predicate(string)) {
						return matcher.exec(string);
					}
					else {
						return alternate.exec(string);
					}
				},

				name,
			};
		}

		let remaining = input;

		const atEnd = () => !remaining.length;

		/**
		 * Convenience function for building simple lexers from a map of token
		 * names to matchers.
		 */
		const choose = (map) => {
			return () => {
				if (typeof map[Symbol.iterator] !== 'function') {
					map = new Map(Object.entries(map));
				}

				for (const [name, matcher] of map) {
					if (peek(matcher)) {
						const text = consume();

						return token(name, text);
					}
				}
			};
		};

		/**
		 * Run a matcher at the current location and consume the input. If the
		 * matcher does not match, throws an error.
		 */
		const consume = (...matchers) => {
			let matcher;
			let result;

			if (matchers.length === 1) {
				matcher = matchers[0];
			}
			else if (matchers.length > 1) {
				matcher = sequence(...matchers);
			}

			// Potentially re-use result of preceeding `peek()`.

			const peeked = peek.peeked;
			delete peek.peeked;

			if (matcher === undefined) {

				// Return result of previous `peek()`.

				if (peeked !== undefined && peeked !== null) {
					result = peeked;
				}
				else {
					throw new Error(
						'Cannot consume() non-existent previous peek() result'
					);
				}
			}
			else {
				if (peeked !== null) {

					// We previously peeked but aren't consuming the memoized
					// result, so we need to rollback the peek's side-effects.

					meta.rollback();
				}

				if (typeof matcher === 'string') {
					matcher = match(matcher);
				}

				result = matcher.exec(remaining);
			}

			if (result === null) {
				fail(matcher);
			}

			remaining = remaining.slice(result[0].length);

			meta.commit();

			return result[0];
		};

		/**
		 * Reports a failure to match.
		 */
		const fail = (reasonOrMatcher) => {
			let reason;

			if (reasonOrMatcher.description) {
				reason = `Failed to match ${reasonOrMatcher.description}`;
			}
			else {
				reason = reasonOrMatcher;
			}

			// TODO: report index, maybe.

			const context =
				remaining.length > 20
					? `${remaining.slice(0, 20)}...`
					: remaining;

			throw new Error(`${reason} at: ${JSON.stringify(context)}`);
		};

		/**
		 * Performs lookahead by testing `matcher` at the current location in
		 * the input. Returns `true` to indicate whether there was a match.
		 *
		 * The peeked match if memoized, such that an immediately
		 * subsequent call to `consume()` without arguments will just access the
		 * memoized match instead of repeating the scan.
		 */
		const peek = (...matchers) => {
			meta.checkpoint();

			let matcher;

			if (matchers.length === 1) {
				matcher = matchers[0];
			}
			else if (matchers.length > 1) {
				matcher = sequence(...matchers);
			}

			if (typeof matcher === 'string') {
				matcher = match(matcher);
			}

			// Memoize the result so that we can `consume()` it if desired.

			peek.peeked = matcher.exec(remaining);

			if (peek.peeked === null) {
				meta.rollback();
			}

			return peek.peeked !== null;
		};

		/**
		 * Produce an object representing a token, given a token `name`
		 * and textual `contents`.
		 */
		const token = (name, contents) => {
			return {
				contents,
				index: input.length - remaining.length - contents.length,
				name,
			};
		};

		/**
		 * @internal
		 *
		 * Allows for inspection of the internal state of the lexer instance.
		 */
		const __DEBUG__ = {
			get index() {
				return input.length - remaining.length;
			},

			get meta() {
				return [...meta.entries()];
			},

			get remaining() {
				return remaining;
			},
		};

		/**
		 * API to be passed to the callback.
		 *
		 * Note that there are some internal functions that we don't pass
		 * (eg. except, name, to, test), but which are returned by other
		 * calls to the API. (eg. `match(...).to(...)`).
		 */
		const API = {
			__DEBUG__,
			a,
			allOf,
			an,
			atEnd,
			choose,
			consume,
			fail,
			lookup,
			match,
			maybe,
			meta,
			never,
			oneOf,
			pass,
			peek,
			repeat,
			sequence,
			token,
			when,
		};

		const advance = this._callback(API);

		if (typeof advance !== 'function') {
			throw new Error(
				'Expected `new Lexer()` callback to return a function'
			);
		}

		let index = 0;

		const produceToken = () => {
			index = input.length - remaining.length;

			const token = advance();

			if (!token) {
				fail('Failed to consume all input');
			}

			if (
				typeof token.name !== 'string' ||
				typeof token.contents !== 'string' ||
				!Number.isInteger(token.index)
			) {
				fail(`Invalid token received at index ${index}`);
			}

			if (!token.contents.length) {
				fail(`Zero-width token ${token.name} produced`);
			}

			return token;
		};

		/**
		 * Count of tokens produced by the iterator so far.
		 */
		let counter = 0;

		const tokens = new Map();

		/**
		 * Defines "next" and "previous" properties on `token`.
		 */
		const defineProperties = (token, counter) => {
			Object.defineProperties(token, {
				next: {

					// Lazy because token N+1 doesn't exist yet when
					// token N is produced.

					get() {
						if (!atEnd() && !tokens.has(counter + 1)) {
							const nextToken = produceToken();

							defineProperties(nextToken, counter + 1);

							tokens.set(counter + 1, nextToken);
						}

						return tokens.get(counter + 1);
					},
				},
				previous: {

					// Non-enumerable so that tokens can be
					// introspected without the clutter.

					value: tokens.get(counter - 1),
				},
			});
		};

		while (!atEnd()) {
			if (tokens.has(counter + 1)) {
				yield tokens.get(++counter);
			}
			else {
				const token = produceToken();

				tokens.set(++counter, token);

				defineProperties(token, counter);

				yield token;
			}
		}
	}

	/**
	 * Look up a matcher by name.
	 */
	lookup(matcher) {
		if (typeof matcher === 'string' && this._matchers.has(matcher)) {
			return this._matchers.get(matcher);
		}
		else if (matcher && typeof matcher.exec === 'function') {
			return matcher;
		}
		else {
			throw new Error('Unable to look up matcher');
		}
	}
}

/**
 * Escapes `literal` for use in a RegExp.
 */
function escape(literal) {

	// https://github.com/benjamingr/RegExp.escape/blob/master/EscapedChars.md

	return literal.replace(/[\^$\\.*+?()[\]{}|]/g, '\\$&');
}

/**
 * Creates a fake "match" object that mimics what you would get from a call to
 * RegExp.prototype.exec().
 */
function getMatchObject(string) {

	// https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/RegExp/exec
	// TODO: needs index and input properties
	// might also want to set lastIndex on regexp

	return [string];
}

export default Lexer;
