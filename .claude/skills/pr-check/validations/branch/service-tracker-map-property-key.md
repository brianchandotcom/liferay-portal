# ServiceTrackerMap Property Key

Flags a `ServiceTrackerMapFactory.openSingleValueMap` or `ServiceTrackerMapFactory.openMultiValueMap` call that spells out the property key overload by hand. `openSingleValueMap(BundleContext, Class, String)` and `openMultiValueMap(BundleContext, Class, String)` map services by the value of one property: they build the `(key=*)` filter, a `PropertyServiceReferenceMapper` for the key, the default customizer, and the default order. A call that passes the same filter, or `null`, and a mapper that only reads the same property as a `String` repeats what the overload builds, and such calls keep being copied, so new code calls the overload.

## Source Formatter Candidate

This validation stands in for a Source Formatter check. Source Formatter catches the same code at format time and in CI without a pr-check run, so the check is the intended home of this rule, and this validation is the guard until it lands. When the check lands in `modules/util/source-formatter`, delete this file and its entry in `SKILL.md`.

- Check: `JavaServiceTrackerCollectionsCheck`
- Source Formatter pull request: https://github.com/ling-alan-huang/liferay-portal/pull/4401
- Ticket: LPD-113239

## Match

`\.java$`

## Command

This check is static and needs no build. It walks each changed Java file that names `ServiceTrackerMapFactory`, skips generated files, and reports each `openSingleValueMap` or `openMultiValueMap` call on `ServiceTrackerMapFactory` that the branch adds or changes and that meets every condition below. Whitespace is ignored when an argument is compared. It uses `command grep` so each file is searched by the system grep rather than the shell's `grep` wrapper, which some environments route to another tool with its own defaults.

- The call has exactly four arguments. A call with a fifth, such as a customizer or a comparator, is not reported.
- The fourth argument is `new PropertyServiceReferenceMapper<>(<key>)` or `new PropertyServiceReferenceMapper<String, ...>(<key>)`, or a lambda whose whole body is `emitter.emit((String)serviceReference.getProperty(<key>))` under any parameter names, where `<key>` is a string literal or a name, such as a constant.
- The third argument is `null`, or the filter `"(<key>=*)"` for a literal key or `"(" + <key> + "=*)"` for a constant. A filter for another key or with more terms is not reported.
- The call is assigned to a variable that the file declares as `ServiceTrackerMap<String, ...>`, since the property key overload returns a map with `String` keys. A call that is returned or passed on, or whose map has another key type, such as `Long`, or is declared outside the file, such as in a superclass, is not reported.

The scan reads each call from `ServiceTrackerMapFactory` to its closing parenthesis, splits the arguments at the commas outside parentheses, braces, angle brackets, and quotes, the way Source Formatter does, and skips a call inside a string. A call counts as changed when the branch adds any line from its first line to its closing parenthesis. Each finding prints as `<path>:<line>: <method> spells out the property key <key>`, where `<line>` is the first line of the call.

A generated file carries the `@generated` Javadoc marker that Service Builder and the other code generators stamp on every file they produce (for example every `*LocalService` interface). The scan matches it case-insensitively, so the `@Generated` annotation on REST Builder output is covered too. That annotation is machine written boilerplate, not hand written `ServiceTrackerMap` code, so any file carrying it is excluded before the scan.

Anchor that match to a Javadoc tag or an annotation rather than searching the whole file for the bare word, or a class that only mentions `@generated` in prose drops out of the scan entirely.

```bash
CALLS='
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

	{
		lines[NR] = $0
		starts[NR] = length(content) + 1
		content = content $0 "\n"
	}

	END {
		line = 1
		offset = 0

		while (match(substr(content, offset + 1), /ServiceTrackerMapFactory\.[[:space:]]*open(Multi|Single)ValueMap\(/)) {
			start = offset + RSTART
			offset = start + RLENGTH - 1

			method = (substr(content, start, RLENGTH) ~ /Single/) ? "openSingleValueMap" : "openMultiValueMap"

			while ((line < NR) && (starts[line + 1] <= start)) {
				line++
			}

			if ((substr(content, start - 1, 1) ~ /[[:alnum:]_.]/) || inside(lines[line], start - starts[line] + 1)) {
				continue
			}

			angles = 0
			count = 1
			depth = 0
			quote = ""
			text = ""

			for (i = offset + 1; i <= length(content); i++) {
				c = substr(content, i, 1)

				if (quote != "") {
					if ((c == "\n") || ((c == quote) && ((slashes % 2) == 0))) {
						quote = ""
					}

					slashes = (c == "\\") ? slashes + 1 : 0
				}
				else if ((c == "\"") || (c == "\047")) {
					quote = c
					slashes = 0
				}
				else if (c ~ /[({]/) {
					depth++
				}
				else if (c ~ /[)}]/) {
					if (depth == 0) {
						break
					}

					depth--
				}
				else if (c == "<") {
					angles++
				}
				else if ((c == ">") && (substr(content, i - 1, 1) != "-")) {
					angles--
				}
				else if ((c == ",") && (depth == 0) && (angles == 0)) {
					parameters[count++] = text
					text = ""

					continue
				}

				text = text c
			}

			parameters[count] = text

			if (count != 4) {
				continue
			}

			filter = parameters[3]
			mapper = parameters[4]

			gsub(/[ \t\n]/, "", filter)
			gsub(/[ \t\n]/, "", mapper)

			key = ""

			if (mapper ~ /^newPropertyServiceReferenceMapper<(String,[^()]+)?>\(("[^"]+"|[[:alnum:]_.]+)\)$/) {
				key = substr(mapper, index(mapper, "(") + 1)
				key = substr(key, 1, length(key) - 1)
			}
			else if (mapper ~ /^\([[:alnum:]_]+,[[:alnum:]_]+\)->[[:alnum:]_]+\.emit\(\(String\)[[:alnum:]_]+\.getProperty\(("[^"]+"|[[:alnum:]_.]+)\)\)$/) {
				split(substr(mapper, 2), names, /[,)]/)

				prefix = "(" names[1] "," names[2] ")->" names[2] ".emit((String)" names[1] ".getProperty("

				if (index(mapper, prefix) == 1) {
					key = substr(mapper, length(prefix) + 1)
					key = substr(key, 1, length(key) - 2)
				}
			}

			if (key == "") {
				continue
			}

			if (key ~ /^"/) {
				expected = "\"(" substr(key, 2, length(key) - 2) "=*)\""
			}
			else {
				expected = "\"(\"+" key "+\"=*)\""
			}

			if (((filter != "null") && (filter != expected)) || !match(substr(content, 1, start - 1), /[[:alnum:]_]+[[:space:]]*=[[:space:]]*$/)) {
				continue
			}

			name = substr(content, RSTART, RLENGTH)

			sub(/[[:space:]]*=[[:space:]]*$/, "", name)

			if (content !~ ("(^|[^[:alnum:]_])ServiceTrackerMap[[:space:]]*<[[:space:]]*String[[:space:]]*,[^;=(){}]+>[[:space:]]+" name "[[:space:]]*[;=]")) {
				continue
			}

			last = line

			while ((last < NR) && (starts[last + 1] <= i)) {
				last++
			}

			for (n = line; n <= last; n++) {
				if (n in changed) {
					print file ":" line ": " method " spells out the property key " key

					break
				}
			}
		}
	}
'

FINDINGS=$(
	bash "${SKILL_DIR}/select_paths.sh" "${MERGE_BASE}" "${VALIDATION_FILE}" | while IFS= read -r FILE
	do
		[[ -f ${FILE} ]] || continue

		command grep --fixed-strings --quiet 'ServiceTrackerMapFactory' "${FILE}" || continue

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

						print range[1], ((size > 1) ? range[2] : 1)
					}
				' \
				| paste -d ' ' -s -
		)

		awk -v added="${ADDED}" -v file="${FILE}" "${CALLS}" "${FILE}"
	done
)

printf '%s' "${FINDINGS}"
```

**Judge this from `${FINDINGS}` and never from the exit status**, which carries no verdict in either direction. The loop's status is whatever its last iteration happened to leave behind, so it reports on the diff's last Java file rather than on the diff. Each iteration ends on a `continue` or on the `awk` scan, which exits 0 whether or not it prints a finding, so a clean branch and a branch with findings both exit 0.

Run the loop from `${REPO_ROOT}`. The changed paths are relative to the repository root, so from any directory below it, every file test fails and the check passes without scanning anything.

Use `while read` rather than a `for` over an unquoted substitution, so a path holding whitespace stays one file rather than splitting into several.

When `${FINDINGS}` is empty, the branch adds no call that spells out a property key, so the validation passes.

Otherwise, the validation fails. Report the message below to the developer with one row for each finding, giving the path, the line, the method, and the key as the finding prints them, and return it as the failure note.

```markdown
**`ServiceTrackerMapFactory` calls spell out a property key.** Each call below passes the `(key=*)` filter, or `null`, and a mapper that only reads that property as a `String`, which is what the property key overload builds. Call `openSingleValueMap(bundleContext, clazz, key)` or `openMultiValueMap(bundleContext, clazz, key)` instead.

| File | Line | Method | Key |
| --- | --- | --- | --- |
| <file> | <line> | <method> | <key> |
```

## Time Estimate

~10-30 sec (static, no build).