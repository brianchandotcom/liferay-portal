# Typed Property Key

Flags a typed property key, such as `service.ranking:Integer`, outside the property strings of a Declarative Services component. The `<key>:<Type>=<value>` syntax is only parsed in the `property` strings of `@Component` and `@OSGiBeanProperties`. A `Dictionary`, a `HashMapDictionaryBuilder`, or `MapUtil.singletonDictionary` takes the typed key literally, so `"service.ranking:Integer"` becomes a property with that exact name, and a filter attribute never parses the syntax either, so a ranking or an order spelled this way silently has no effect.

## Source Formatter Candidate

This validation stands in for a Source Formatter check. Source Formatter catches the same code at format time and in CI without a pr-check run, so the check is the intended home of this rule, and this validation is the guard until it lands. When the check lands in `modules/util/source-formatter`, delete this file and its entry in `SKILL.md`.

- Check: `JavaTypedPropertyKeyCheck`
- Source Formatter pull request: https://github.com/ling-alan-huang/liferay-portal/pull/4403
- Ticket: LPD-113240

## Match

`\.java$`

## Command

This check is static and needs no build. It walks each changed Java file, skips generated files and files that import an `aQute.bnd.` package, since bnd parses the typed syntax itself, and reports each typed key on a line the branch adds. A typed key is a key of letters, digits, `_`, `.`, and `-`, then `:` and one of the types Declarative Services parses: `Boolean`, `Byte`, `Character`, `Double`, `Float`, `Integer`, `Long`, `Short`, or `String`. It uses `command grep` so each file is searched by the system grep rather than the shell's `grep` wrapper, which some environments route to another tool with its own defaults. A typed key is reported in two places:

- A string literal that is exactly the typed key, such as `"service.ranking:Integer"`. A component property string with a value, such as `"service.ranking:Integer=100"`, is not reported, and neither is a typed key quoted inside a longer string, such as `"A \"key:Long\" in a message"`.
- A filter attribute inside a string, which is `(`, the typed key, and then `=`, `~`, `<`, or `>`, such as `"(service.ranking:Integer=1)"` or `"(&(a=1)(order:Double>=1))"`.

Quotes are tracked on each line on its own, the way Source Formatter tracks them, with escaped quotes and character literals, and the scan reads only the lines the branch adds. Each finding prints as `<path>:<line>: <typed key>`, with ` in a filter` at the end for a filter attribute, where `<line>` is the added line that holds the key.

A generated file carries the `@generated` Javadoc marker that Service Builder and the other code generators stamp on every file they produce (for example every `*LocalService` interface). The scan matches it case-insensitively, so the `@Generated` annotation on REST Builder output is covered too. That annotation is machine written boilerplate, not hand written property code, so any file carrying it is excluded before the scan.

Anchor that match to a Javadoc tag or an annotation rather than searching the whole file for the bare word, or a class that only mentions `@generated` in prose drops out of the scan entirely.

```bash
KEYS='
	function inside(s, pos,    c, delimiter, i, j, quoted, slashes) {
		for (i = 1; i <= pos; i++) {
			c = substr(s, i, 1)

			if (quoted) {
				if (c == delimiter) {
					slashes = 0

					for (j = i - 1; (j > 0) && (substr(s, j, 1) == "\\"); j--) {
						slashes++
					}

					if ((slashes % 2) == 0) {
						quoted = 0
					}
				}
			}
			else if ((c == "\"") || (c == "\047")) {
				delimiter = c
				quoted = 1
			}
		}

		return quoted
	}

	BEGIN {
		size = split(added, list, " ")

		for (i = 1; i < size; i += 2) {
			for (j = 0; j < list[i + 1]; j++) {
				changed[list[i] + j] = 1
			}
		}
	}

	(NR in changed) {
		offset = 0
		text = $0

		while (match(text, /"[[:alnum:]_.-]+:(Boolean|Byte|Character|Double|Float|Integer|Long|Short|String)"|\([[:alnum:]_.-]+:(Boolean|Byte|Character|Double|Float|Integer|Long|Short|String)[=~<>]/)) {
			column = offset + RSTART
			key = substr(text, RSTART + 1, RLENGTH - 2)
			offset = column + RLENGTH - 1
			text = substr(text, RSTART + RLENGTH)

			if (substr($0, column, 1) == "(") {
				if (inside($0, column)) {
					print file ":" NR ": " key " in a filter"
				}
			}
			else if (!inside($0, column - 1)) {
				print file ":" NR ": " key
			}
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
			--regexp='^import aQute\.bnd\.' \
			"${FILE}"
		then
			continue
		fi

		ADDED=$(
			git diff --unified=0 "${MERGE_BASE}...HEAD" -- "${FILE}" \
				| awk '
					/^@@/ {
						size = split(substr($3, 2), range, ",")

						print range[1], ((size > 1) ? range[2] : 1)
					}
				' \
				| paste -d ' ' -s -
		)

		awk -v added="${ADDED}" -v file="${FILE}" "${KEYS}" "${FILE}"
	done
)

printf '%s' "${FINDINGS}"
```

**Judge this from `${FINDINGS}` and never from the exit status**, which carries no verdict in either direction. The loop's status is whatever its last iteration happened to leave behind, so it reports on the diff's last Java file rather than on the diff. Each iteration ends on a `continue` or on the `awk` scan, which exits 0 whether or not it prints a finding, so a clean branch and a branch with findings both exit 0.

Run the loop from `${REPO_ROOT}`. The changed paths are relative to the repository root, so from any directory below it, every file test fails and the check passes without scanning anything.

Use `while read` rather than a `for` over an unquoted substitution, so a path holding whitespace stays one file rather than splitting into several.

When `${FINDINGS}` is empty, the branch adds no typed key outside component property strings, so the validation passes.

Otherwise, the validation fails. Report the message below to the developer with one row for each finding, giving the path, the line, and the typed key as the finding prints them, and the plain key, which is the typed key without `:<Type>`, and return it as the failure note.

```markdown
**Typed property keys are only parsed in component property strings.** Each key below is a string literal that is exactly a typed key, or a filter attribute in a string. Only the `property` strings of `@Component` and `@OSGiBeanProperties` parse the `:<Type>` syntax, so a `Dictionary`, a `HashMapDictionaryBuilder`, `MapUtil.singletonDictionary`, or a filter takes the key literally, and the property or the match has no effect. Use the plain key, with a value of the named type for a property.

| File | Line | Typed Key | Plain Key |
| --- | --- | --- | --- |
| <file> | <line> | <typed key> | <plain key> |
```

## Time Estimate

~10-30 sec (static, no build).