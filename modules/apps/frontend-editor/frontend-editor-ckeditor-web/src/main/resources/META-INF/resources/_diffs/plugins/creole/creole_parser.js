/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

/*
 * JavaScript Creole 1.0 Wiki Markup Parser
 * $Id: creole.js 14 2009-03-21 16:15:08Z ifomichev $
 *
 * Copyright (c) 2009 Ivan Fomichev
 *
 * Portions Copyright (c) 2007 Chris Purcell
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

(function() {
	if (!Parse) {
		var Parse = {};
	}
	if (!Parse.Simple) {
		Parse.Simple = {};
	}

	Parse.Simple.Base = function(grammar, options) {
		if (!arguments.length) {
			return;
		}

		this.grammar = grammar;
		this.grammar.root = new this.ruleConstructor(this.grammar.root);
		this.options = options;
	};

	Parse.Simple.Base.prototype = {
		grammar: null,
		options: null,

		parse(node, data, options) {
			if (options) {
				for (var i in this.options) {
					if (typeof options[i] == 'undefined') {
						options[i] = this.options[i];
					}
				}
			}
			else {
				options = this.options;
			}
			data = data.replace(/\r\n?/g, '\n');
			this.grammar.root.apply(node, data, options);
			if (options && options.forIE) {
				node.innerHTML = node.innerHTML.replace(/\r?\n/g, '\r\n');
			}
		},

		ruleConstructor: null
	};

	Parse.Simple.Base.prototype.constructor = Parse.Simple.Base;

	Parse.Simple.Base.Rule = function(params) {
		if (!arguments.length) {
			return;
		}

		for (var p in params) {
			this[p] = params[p];
		}
		if (!this.children) {
			this.children = [];
		}
	};

	Parse.Simple.Base.prototype.ruleConstructor = Parse.Simple.Base.Rule;

	Parse.Simple.Base.Rule.prototype = {
		apply(node, data, options) {
			var tail = '' + data;
			var matches = [];

			if (!this.fallback.apply) {
				this.fallback = new this.constructor(this.fallback);
			}

			while (true) {
				var best = false;
				var rule = false;
				for (let i = 0; i < this.children.length; i++) {
					if (typeof matches[i] == 'undefined') {
						if (!this.children[i].match) {
							this.children[i] = new this.constructor(
								this.children[i]
							);
						}
						matches[i] = this.children[i].match(tail, options);
					}
					if (
						matches[i] &&
						(!best || best.index > matches[i].index)
					) {
						best = matches[i];
						rule = this.children[i];
						if (best.index == 0) {
							break;
						}
					}
				}

				var pos = best ? best.index : tail.length;
				if (pos > 0) {
					this.fallback.apply(node, tail.substring(0, pos), options);
				}

				if (!best) {
					break;
				}

				if (!rule.build) {
					rule = new this.constructor(rule);
				}
				rule.build(node, best, options);

				var chopped = best.index + best[0].length;
				tail = tail.substring(chopped);
				for (let i = 0; i < this.children.length; i++) {
					if (matches[i]) {
						if (matches[i].index >= chopped) {
							matches[i].index -= chopped;
						}
						else {
							matches[i] = void 0;
						}
					}
				}
			}

			return this;
		},

		attrs: null,

		build(node, r, options) {
			var data;
			if (this.capture !== null) {
				data = r[this.capture];
			}

			var target;
			if (this.tag) {
				target = document.createElement(this.tag);
				node.appendChild(target);
			}
			else {
				target = node;
			}

			if (data) {
				if (this.replaceRegex) {
					data = data.replace(this.replaceRegex, this.replaceString);
				}
				this.apply(target, data, options);
			}

			if (this.attrs) {
				for (var i in this.attrs) {
					target.setAttribute(i, this.attrs[i]);
					if (options && options.forIE && i == 'class') {
						target.className = this.attrs[i];
					}
				}
			}
			return this;
		},

		capture: null,
		children: null,

		fallback: {
			apply(node, data, options) {
				if (options && options.forIE) {
					// workaround for bad IE
					data = data.replace(/\n/g, ' \r');
				}
				node.appendChild(document.createTextNode(data));
			}
		},

		match(data) {
			return data.match(this.regex);
		},

		regex: null,
		replaceRegex: null,
		replaceString: null,
		tag: null
	};

	Parse.Simple.Base.Rule.prototype.constructor = Parse.Simple.Base.Rule;

	Parse.Simple.Creole = function(options) {
		var rx = {};
		rx.link = '[^\\]|~\\n]*(?:(?:\\](?!\\])|~.)[^\\]|~\\n]*)*';
		rx.linkText = '[^\\]~\\n]*(?:(?:\\](?!\\])|~.)[^\\]~\\n]*)*';
		rx.uriPrefix = '\\b(?:(?:https?|ftp)://|mailto:)';
		rx.uri = rx.uriPrefix + rx.link;
		rx.rawUri = rx.uriPrefix + '\\S*[^\\s!"\',.:;?]';
		rx.interwikiPrefix = '[\\w.]+:';
		rx.interwikiLink = rx.interwikiPrefix + rx.link;
		rx.img =
			'\\{\\{((?!\\{)[^|}\\n]*(?:}(?!})[^|}\\n]*)*)' +
			(options && options.strict ? '' : '(?:') +
			'\\|([^}~\\n]*((}(?!})|~.)[^}~\\n]*)*)' +
			(options && options.strict ? '' : ')?') +
			'}}';

		var formatLink = function(link, format) {
			if (format instanceof Function) {
				return format(link);
			}

			format = format instanceof Array ? format : [format];
			if (typeof format[1] == 'undefined') {
				format[1] = '';
			}
			return format[0] + link + format[1];
		};

		var g = {
			br: {regex: /\\\\/, tag: 'br'},

			em: {
				capture: 1,
				regex:
					'\\/\\/(((?!' +
					rx.uriPrefix +
					')[^\\/~])*' +
					'((' +
					rx.rawUri +
					'|\\/(?!\\/)|~(.|(?=\\n)|$))' +
					'((?!' +
					rx.uriPrefix +
					')[^\\/~])*)*)(\\/\\/|\\n|$)',
				tag: 'em'
			},

			escapedSequence: {
				attrs: {class: 'escaped'},
				capture: 1,
				regex: '~(' + rx.rawUri + '|.)',
				tag: 'span'
			},

			escapedSymbol: {
				attrs: {class: 'escaped'},
				capture: 1,
				regex: /~(.)/,
				tag: 'span'
			},

			hr: {regex: /(^|\n)\s*----\s*(\n|$)/, tag: 'hr'},

			img: {
				build(node, r, options) {
					var imagePath = r[1];
					var imagePathPrefix = options ? options.imagePrefix : '';

					if (imagePathPrefix) {
						if (!/^https?:\/\//gi.test(imagePath)) {
							imagePath = imagePathPrefix + imagePath;
						}
					}

					var img = document.createElement('img');
					img.src = imagePath;
					if (r[2]) {
						img.alt = r[2].replace(/~(.)/g, '$1');
					}
					else if (options && options.defaultImageText) {
						img.alt = options.defaultImageText;
					}
					node.appendChild(img);
				},
				regex: rx.img
			},

			li: {
				capture: 0,
				regex: /[ \t]*([*#]).+(\n[ \t]*[^*#\s].*)*(\n[ \t]*[*#]{2}.+)*/,
				replaceRegex: /(^|\n)[ \t]*[*#]/g,
				replaceString: '$1',
				tag: 'li'
			},

			namedLink: {
				build(node, r, options) {
					var link = document.createElement('a');

					link.href =
						options && options.linkFormat
							? formatLink(
									r[1].replace(/~(.)/g, '$1'),
									options.linkFormat
							  )
							: r[1].replace(/~(.)/g, '$1');
					link.setAttribute('data-cke-saved-href', link.href);

					this.apply(link, r[2], options);

					node.appendChild(link);
				},
				regex: '\\[\\[(' + rx.link + ')\\|(' + rx.linkText + ')\\]\\]'
			},

			namedUri: {
				build(node, r, options) {
					var link = document.createElement('a');
					link.href = r[1];
					if (options && options.isPlainUri) {
						link.appendChild(document.createTextNode(r[2]));
					}
					else {
						this.apply(link, r[2], options);
					}
					node.appendChild(link);
				},
				regex: '\\[\\[(' + rx.uri + ')\\|(' + rx.linkText + ')\\]\\]'
			},

			olist: {
				capture: 0,
				regex: /(^|\n)([ \t]*#[^*#].*(\n|$)([ \t]*[^\s*#].*(\n|$))*([ \t]*[*#]{2}.*(\n|$))*)+/,
				tag: 'ol'
			},

			paragraph: {capture: 0, regex: /(^|\n)(\s*\S.*(\n|$))/, tag: 'p'},

			preBlock: {
				capture: 2,
				regex: /(^|\n)\{\{\{\n((.*\n)*?)\}\}\}(\n|$)/,
				replaceRegex: /^ ([ \t]*\}\}\})/gm,
				replaceString: '$1',
				tag: 'pre'
			},

			rawUri: {build: 'dummy', regex: '(' + rx.rawUri + ')'},

			singleLine: {capture: 0, regex: /.+/},

			strong: {
				capture: 1,
				regex: /\*\*([^*~]*((\*(?!\*)|~(.|(?=\n)|$))[^*~]*)*)(\*\*|\n|$)/,
				tag: 'strong'
			},

			table: {
				attrs: {class: 'cke_show_border'},
				capture: 0,
				regex: /(^|\n)(\|.*?[ \t]*(\n|$))+/,
				tag: 'table'
			},

			td: {
				capture: 1,
				regex:
					'\\|([^|~\\[{]*((~(.|(?=\\n)|$)|' +
					'(?:\\[\\[' +
					rx.link +
					'(\\|' +
					rx.linkText +
					')?\\]\\][^|~\\[{]*)*' +
					(options && options.strict ? '' : '|' + rx.img) +
					'|[\\[{])[^|~]*)*)',
				tag: 'td'
			},

			text: {capture: 0, regex: /(^|\n)(\s*[^\s].*(\n|$))+/},

			th: {capture: 1, regex: /\|+=([^|]*)/, tag: 'th'},

			tr: {capture: 2, regex: /(^|\n)(\|.*?)\|?[ \t]*(\n|$)/, tag: 'tr'},

			tt: {
				capture: 1,
				regex: /\{\{\{(.*?\}\}\}+)/,
				replaceRegex: /\}\}\}$/,
				replaceString: '',
				tag: 'tt'
			},

			ulist: {
				capture: 0,
				regex: /(^|\n)([ \t]*\*[^*#].*(\n|$)([ \t]*[^\s*#].*(\n|$))*([ \t]*[*#]{2}.*(\n|$))*)+/,
				tag: 'ul'
			},

			unnamedInterwikiLink: {
				build: 'dummy',
				regex: '\\[\\[(' + rx.interwikiLink + ')\\]\\]'
			},

			unnamedLink: {
				build: 'dummy',
				regex: '\\[\\[(' + rx.link + ')\\]\\]'
			},

			unnamedUri: {build: 'dummy', regex: '\\[\\[(' + rx.uri + ')\\]\\]'}
		};
		g.unnamedUri.build = g.rawUri.build = function(node, r, options) {
			if (!options) {
				options = {};
			}
			options.isPlainUri = true;
			g.namedUri.build.call(this, node, Array(r[0], r[1], r[1]), options);
			options.isPlainUri = false;
		};
		g.unnamedLink.build = function(node, r, options) {
			g.namedLink.build.call(
				this,
				node,
				Array(r[0], r[1], r[1]),
				options
			);
		};
		g.namedInterwikiLink = {
			build(node, r, options) {
				var link = document.createElement('a');

				var m, f;
				if (options && options.interwiki) {
					m = r[1].match(/(.*?):(.*)/);
					f = options.interwiki[m[1]];
				}

				if (typeof f == 'undefined') {
					if (!g.namedLink.apply) {
						g.namedLink = new this.constructor(g.namedLink);
					}
					return g.namedLink.build.call(
						g.namedLink,
						node,
						r,
						options
					);
				}

				link.href = formatLink(m[2].replace(/~(.)/g, '$1'), f);

				this.apply(link, r[2], options);

				node.appendChild(link);
			},
			regex:
				'\\[\\[(' + rx.interwikiLink + ')\\|(' + rx.linkText + ')\\]\\]'
		};
		g.unnamedInterwikiLink.build = function(node, r, options) {
			g.namedInterwikiLink.build.call(
				this,
				node,
				Array(r[0], r[1], r[1]),
				options
			);
		};
		g.namedUri.children = g.unnamedUri.children = g.rawUri.children = g.namedLink.children = g.unnamedLink.children = g.namedInterwikiLink.children = g.unnamedInterwikiLink.children = [
			g.escapedSymbol,
			g.img
		];

		for (var i = 1; i <= 6; i++) {
			g['h' + i] = {
				capture: 2,
				regex:
					'(^|\\n)[ \\t]*={' +
					i +
					'}[ \\t]*' +
					'([^\\n=][^~]*?(~(.|(?=\\n)|$))*)[ \\t]*=*\\s*(\\n|$)',
				tag: 'h' + i
			};
		}

		g.ulist.children = g.olist.children = [g.li];
		g.li.children = [g.ulist, g.olist];
		g.li.fallback = g.text;

		g.table.children = [g.tr];
		g.tr.children = [g.th, g.td];
		g.td.children = [g.singleLine];
		g.th.children = [g.singleLine];

		g.h1.children = g.h2.children = g.h3.children = g.h4.children = g.h5.children = g.h6.children = [
			g.escapedSequence,
			g.br,
			g.rawUri,
			g.namedUri,
			g.namedInterwikiLink,
			g.namedLink,
			g.unnamedUri,
			g.unnamedInterwikiLink,
			g.unnamedLink,
			g.tt,
			g.img
		];

		g.singleLine.children = g.paragraph.children = g.text.children = g.strong.children = g.em.children = [
			g.escapedSequence,
			g.strong,
			g.em,
			g.br,
			g.rawUri,
			g.namedUri,
			g.namedInterwikiLink,
			g.namedLink,
			g.unnamedUri,
			g.unnamedInterwikiLink,
			g.unnamedLink,
			g.tt,
			g.img
		];

		g.root = {
			children: [
				g.h1,
				g.h2,
				g.h3,
				g.h4,
				g.h5,
				g.h6,
				g.hr,
				g.ulist,
				g.olist,
				g.preBlock,
				g.table
			],
			fallback: {children: [g.paragraph]}
		};

		Parse.Simple.Base.call(this, g, options);
	};

	Parse.Simple.Creole.prototype = new Parse.Simple.Base();

	Parse.Simple.Creole.prototype.constructor = Parse.Simple.Creole;

	CKEDITOR.CreoleParser = Parse.Simple.Creole;
})();
