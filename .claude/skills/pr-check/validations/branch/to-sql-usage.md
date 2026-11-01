# toSQL Usage

Flags a statement that calls `toSQL(ASTNodeListener)` and drops what it returns. That overload builds the whole SQL text of the node in a `StringBundler` and returns it, so a call that only walks the tree for its listener builds a string that nobody reads. Such a call passes a consumer that ignores the text instead, as in `predicate.toSQL(string -> {}, astNodeListener)`, which walks the same nodes without building the string.

## Source Formatter Candidate

This validation stands in for a Source Formatter check. Source Formatter catches the same code at format time and in CI without a pr-check run, so the check is the intended home of this rule, and this validation is the guard until it lands. When the check lands in `modules/util/source-formatter`, delete this file and its entry in `SKILL.md`.

- Check: `UnusedToSQLStringCheck`
- Source Formatter pull request: https://github.com/ling-alan-huang/liferay-portal/pull/4412
- Ticket: LPD-113238

## Match

`\.java$`

## Command

This check is static and needs no build. It walks each changed Java file, skips generated files, and reports each statement that starts on a line the branch adds and is a `toSQL` call whose result is discarded: the line starts with the receiver of the call, the previous line that is not blank ends with `;`, `{`, or `}`, the argument list has no comma outside nested parentheses, brackets, and braces, and a semicolon follows the closing parenthesis. String literals are left out before the scan. It uses `command grep` so each file and its added lines are searched by the system grep rather than the shell's `grep` wrapper, which some environments route to another tool with its own defaults. A call whose result is assigned, returned, or passed on is not reported, and neither is a call that already passes a consumer. Each finding prints as `<path>:<line>: discards the SQL string of toSQL`, where `<line>` is the first line of the call.

A generated file carries the `@generated` Javadoc marker that Service Builder and the other code generators stamp on every file they produce (for example every `*LocalService` interface). The scan matches it case-insensitively, so the `@Generated` annotation on REST Builder output is covered too. That annotation is machine written boilerplate, not hand written `toSQL` code, so any file carrying it is excluded before the `toSQL` scan.

Anchor that match to a Javadoc tag or an annotation rather than searching the whole file for the bare word, or a class that only mentions `@generated` in prose drops out of the scan entirely.

```bash
CALLS='
	function scan(text,    c, i) {
		for (i = 1; i <= length(text); i++) {
			c = substr(text, i, 1)

			if (closed) {
				if (c ~ /[[:space:]]/) {
					continue
				}

				if ((c == ";") && (commas == 0)) {
					print start
				}

				closed = 0
				inside = 0

				return
			}

			if (c ~ /[([{]/) {
				depth++
			}
			else if (c ~ /[)}]/ || c == "]") {
				if (depth == 0) {
					closed = 1
				}
				else {
					depth--
				}
			}
			else if ((c == ",") && (depth == 0)) {
				commas++
			}
		}
	}

	{
		text = $0

		gsub(/"([^"\\]|\\.)*"/, "", text)

		if (inside) {
			scan(text)
		}
		else if ((text ~ /^[[:space:]]*[[:alpha:]_][[:alnum:]_.()]*\.toSQL\(/) && (previous ~ /[;{}][[:space:]]*$/)) {
			closed = 0
			commas = 0
			depth = 0
			inside = 1
			start = NR

			scan(substr(text, index(text, ".toSQL(") + 7))
		}

		if (text ~ /[^[:space:]]/) {
			previous = text
		}
	}
'

FINDINGS=$(
	bash "${SKILL_DIR}/select_paths.sh" "${MERGE_BASE}" "${VALIDATION_FILE}" | while IFS= read -r FILE
	do
		[[ -f ${FILE} ]] || continue

		if command grep \
			--extended-regexp \
			--ignore-case \
			--quiet \
			--regexp='^[[:space:]]*(\*[[:space:]]*@generated|@generated\()' \
			"${FILE}"
		then
			continue
		fi

		ADDED=$(
			git diff --unified=0 "${MERGE_BASE}...HEAD" -- "${FILE}" \
				| awk '
					/^@@/ {
						size = split(substr($3, 2), range, ",")

						count = (size > 1) ? range[2] : 1

						for (i = 0; i < count; i++) {
							print range[1] + i
						}
					}
				'
		)

		awk "${CALLS}" "${FILE}" | while IFS= read -r LINE
		do
			if command grep --fixed-strings --line-regexp --quiet "${LINE}" <<< "${ADDED}"
			then
				echo "${FILE}:${LINE}: discards the SQL string of toSQL"
			fi
		done
	done
)

printf '%s' "${FINDINGS}"
```

**Judge this from `${FINDINGS}` and never from the exit status**, which carries no verdict in either direction. The loop's status is whatever its last iteration happened to leave behind, so it reports on the diff's last Java file rather than on the diff. Each iteration ends on a `continue` or on the loop over the lines the `awk` scan prints, and every pass of that loop ends on an `if` that prints a finding or not and exits 0 either way, so a clean branch and a branch with findings both exit 0.

Run the loop from `${REPO_ROOT}`. The changed paths are relative to the repository root, so from any directory below it, every file test fails and the check passes without scanning anything.

Use `while read` rather than a `for` over an unquoted substitution, so a path holding whitespace stays one file rather than splitting into several.

When `${FINDINGS}` is empty, the branch adds no `toSQL` call that discards its string, so the validation passes.

Otherwise, the validation fails. Report the message below to the developer with one row for each finding, giving the path and the line as the finding prints them, and return it as the failure note.

```markdown
**`toSQL` builds a SQL string that nobody reads.** Each call below passes no consumer and drops what `toSQL` returns, so `toSQL` builds the whole SQL text for nothing. Pass a consumer that ignores the text, as in `toSQL(string -> {}, astNodeListener)`.

| File | Line |
| --- | --- |
| <file> | <line> |
```

## Time Estimate

~10-30 sec (static, no build).