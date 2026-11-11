# AUI Value Function

Flags an `aui:input` or `aui:select` that the branch adds or changes and that reads a bean property without a `valueFunction`. The tags take the getter of the bean as a method reference, such as `<aui:input name="comments" type="textarea" valueFunction="<%= Organization::getComments %>" />`, so the compiler checks the property. A tag without a `valueFunction` reads only its `value` attribute and the request, so a field that should show the bean's value stays empty.

## Source Formatter Candidate

This validation stands in for a Source Formatter check. Source Formatter catches the same code at format time and in CI without a pr-check run, so the check is the intended home of this rule, and this validation is the guard until it lands. When the check lands in `modules/util/source-formatter`, delete this file and its entry in `SKILL.md`.

- Check: `AUIValueFunctionCheck`
- Source Formatter pull request: https://github.com/ling-alan-huang/liferay-portal/pull/4421
- Ticket: LPD-115344

## Match

`\.(jsp|jspf)$ &! ^modules/third-party/`

## Command

This check is static and needs no build. It walks each changed `.jsp` and `.jspf` file that holds an `aui:input` or an `aui:select`, and reads every tag to its closing `>`, skipping quoted attribute values. A tag is a candidate when it has no `valueFunction` and reads a bean by name:

- An `aui:input` with a `type` of `color`, `email`, `number`, `range`, `tel`, `text` or `textarea`, or a `type` expression that holds one of them as a string literal, unless `ignoreRequestValue` is `<%= true %>`. The property is the `name` of the input, when it is a plain identifier.
- An `aui:input` with a `type` of `hidden` or `timeZone` and no `value` attribute. The property is the `name` of the input, or the `field` of a time zone input that has one. A time zone input with an empty `value` counts as having none.
- An `aui:select`. With a `listType`, the property is the `listTypeFieldName`, or `listTypeId` when the tag has none. Without one, the property is the `name` of the select.

A hidden or time zone `aui:input` reads its `valueFunction` only when its `value` is `null`, so the two do not go together on these inputs: an input whose `type` is `hidden` or `timeZone` and that has both a `value` and a `valueFunction` is reported as well.

The model of a candidate is the declared type of its bean: the type of the `bean` variable of the tag, or of the last `aui:model-context` before the tag, as the file declares it. When the file does not declare the variable, the model is the class in the `model` attribute next to that `bean`. An `aui:model-context` without a `bean`, or with `<%= null %>` as its `bean`, gives the tag nothing to read, so the candidate passes. When the file has no `aui:model-context` before the tag, the models are the ones of every `aui:model-context` with a `bean` in the files of the same web root that include the file by its name. A candidate is reported when the source of one of its models, `<Model>.java` or `<Model>Model.java`, declares the getter of the property, `get` followed by the property name with its first letter in upper case. A candidate whose models have no such getter is not a bean property and passes, except an `aui:select` with a `listType`: such a select shows the list type of its bean, so a property that a model with a source has no getter for is reported as a mistake.

The finding is reported only when the branch adds or changes a line of the tag. The check uses `command grep`, or `grep` run by `xargs`, so each file is searched by the system grep rather than the shell's `grep` wrapper, which some environments route to another tool with its own defaults. Each finding prints as `<path>:<line>: "aui:<tag>" does not pass the getter of "<property>" of "<Model>", add valueFunction="<%= <Model>::<getter> %>"`, as `<path>:<line>: "aui:input" has both "value" and "valueFunction", remove one of them` for an input that has both, or as `<path>:<line>: "<Model>" has no "<getter>" for the list type of the "aui:select", set "listTypeFieldName" to the list type property of the bean` for such a select.

```bash
BEAN_READS='
	function attribute(tag, name,    pattern, value) {
		pattern = "[[:space:]]" name "=(\"([^\"\\\\]|\\\\.)*\"|\047([^\047\\\\]|\\\\.)*\047)"

		if (!match(tag, pattern)) {
			return ""
		}

		value = substr(tag, RSTART, RLENGTH)

		sub(/^[^=]*=./, "", value)

		return substr(value, 1, length(value) - 1)
	}

	function bean(tag,    value) {
		value = attribute(tag, "bean")

		if (value ~ /^<%=[[:space:]]*null[[:space:]]*%>$/) {
			return ""
		}

		return value
	}

	function declared(tag,    name, value) {
		name = bean(tag)

		if (name !~ /^<%=[[:space:]]*[[:alnum:]_]+[[:space:]]*%>$/) {
			return ""
		}

		gsub(/^<%=[[:space:]]*|[[:space:]]*%>$/, "", name)

		if (!match(text, "[[:space:](][[:alnum:]_.]+[[:space:]]+" name "[[:space:]]*(;|=[^\"\047=])")) {
			return ""
		}

		value = substr(text, RSTART + 1, RLENGTH - 1)

		sub(/[[:space:]].*/, "", value)
		sub(/.*\./, "", value)

		if (value == "return") {
			return ""
		}

		return value
	}

	function model(tag,    value) {
		value = attribute(tag, "model")

		if (!match(value, /[[:alnum:]_.]+\.class/)) {
			return ""
		}

		value = substr(value, RSTART, RLENGTH - 6)

		sub(/.*\./, "", value)

		return value
	}

	function valued(tag, type) {
		return (tag ~ /[[:space:]]value=/) && !((type == "timeZone") && (attribute(tag, "value") == ""))
	}

	{
		text = text $0 "\n"
	}

	END {
		line = 1
		n = length(text)

		for (i = 1; i <= n; i++) {
			ch = substr(text, i, 1)

			if (ch == "\n") {
				line++

				continue
			}

			if ((ch != "<") || (substr(text, i, 5) != "<aui:")) {
				continue
			}

			if (!match(substr(text, i + 5, 14), /^(input|model-context|select)[[:space:]\/>]/)) {
				continue
			}

			name = substr(text, i + 5, RLENGTH - 1)
			start = line
			quote = ""

			for (j = i + 5; j <= n; j++) {
				c = substr(text, j, 1)

				if (c == "\n") {
					line++
				}

				if (quote != "") {
					if (c == "\\") {
						j++
					}
					else if (c == quote) {
						quote = ""
					}
				}
				else if ((c == "\"") || (c == "\047")) {
					quote = c
				}
				else if (c == ">") {
					break
				}
			}

			tag = substr(text, i, j - i + 1)

			gsub(/\n/, " ", tag)

			i = j

			if (name == "model-context") {
				context = ""
				seen = 1

				if (bean(tag) != "") {
					context = declared(tag)

					if (context == "") {
						context = model(tag)
					}
				}

				continue
			}

			if (attribute(tag, "valueFunction") != "") {
				if ((name == "input") && (attribute(tag, "type") ~ /^(hidden|timeZone)$/) && valued(tag, attribute(tag, "type"))) {
					print start "\t" line "\t" name "\t-\t!"
				}

				continue
			}

			if (name == "select") {
				if (attribute(tag, "listType") == "") {
					property = attribute(tag, "name")
				}
				else {
					name = "listTypeSelect"

					property = attribute(tag, "listTypeFieldName")

					if (property == "") {
						property = "listTypeId"
					}
				}
			}
			else {
				type = attribute(tag, "type")

				property = attribute(tag, "name")

				if ((type ~ /^(color|email|number|range|tel|text|textarea)$/) || (type ~ /^<%.*"(color|email|number|range|tel|text|textarea)"/)) {
					if (attribute(tag, "ignoreRequestValue") ~ /^<%=[[:space:]]*true[[:space:]]*%>$/) {
						continue
					}
				}
				else if ((type ~ /^(hidden|timeZone)$/) || (type ~ /^<%.*"(hidden|timeZone)"/)) {
					if (valued(tag, type)) {
						continue
					}

					if ((type ~ /timeZone/) && (attribute(tag, "field") != "")) {
						property = attribute(tag, "field")
					}
				}
				else {
					continue
				}
			}

			if (property !~ /^[[:alnum:]_]+$/) {
				continue
			}

			if (bean(tag) != "") {
				type = declared(tag)

				if (type == "") {
					type = model(tag)
				}

				print start "\t" line "\t" name "\t" property "\t" type
			}
			else if (seen) {
				print start "\t" line "\t" name "\t" property "\t" context
			}
			else {
				print start "\t" line "\t" name "\t" property "\t?"
			}
		}
	}
'

FINDINGS=$(
	bash "${SKILL_DIR}/select_paths.sh" "${MERGE_BASE}" "${VALIDATION_FILE}" | while IFS= read -r FILE
	do
		[ -f ${FILE} ] || continue

		command grep --extended-regexp --quiet --regexp='<aui:(input|select)' "${FILE}" || continue

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

		awk "${BEAN_READS}" "${FILE}" | while IFS=$'\t' read -r START END TAG PROPERTY MODELS
		do
			CHANGED=

			for ((LINE = START; LINE <= END; LINE++))
			do
				if command grep --fixed-strings --line-regexp --quiet "${LINE}" <<< "${ADDED}"
				then
					CHANGED=true

					break
				fi
			done

			[[ -n ${CHANGED} ]] || continue

			if [[ ${MODELS} == "!" ]]
			then
				echo "${FILE}:${START}: \"aui:input\" has both \"value\" and \"valueFunction\", remove one of them"

				continue
			fi

			if [[ ${MODELS} == "?" ]]
			then
				WEB_ROOT=${FILE%%/META-INF/resources/*}

				[[ ${WEB_ROOT} == "${FILE}" ]] && WEB_ROOT=${FILE%%/docroot/*}

				MODELS=$(
					command grep \
						--extended-regexp \
						--files-with-matches \
						--include='*.jsp' \
						--include='*.jspf' \
						--recursive \
						--regexp="include file=\"[^\"]*${FILE##*/}\"" \
						"${WEB_ROOT}" \
						| xargs \
							--no-run-if-empty \
							grep \
							--extended-regexp \
							--no-filename \
							--only-matching \
							--regexp='<aui:model-context bean="<%= .* model="<%= [[:alnum:]_.]+\.class' \
						| command grep --invert-match --regexp='bean="<%= null %>"' \
						| sed -E 's/.*[ .]([[:alnum:]_]+)\.class$/\1/' \
						| sort --unique
				)
			fi

			LIST_TYPE=

			if [[ ${TAG} == listTypeSelect ]]
			then
				LIST_TYPE=true
				TAG=select
			fi

			GETTER="get$(tr '[:lower:]' '[:upper:]' <<< "${PROPERTY:0:1}")${PROPERTY:1}"

			UNMATCHED=

			for MODEL in ${MODELS}
			do
				SOURCES=$(git ls-files -- "*/${MODEL}.java" "*/${MODEL}Model.java")

				if xargs \
					--no-run-if-empty \
					grep \
					--extended-regexp \
					--regexp="[]>_[:alnum:]][[:space:]]+${GETTER}\(\)[[:space:]]*(throws[^{;]*)?[{;]" <<< "${SOURCES}" \
					| command grep --invert-match --quiet --regexp='return[[:space:]]'
				then
					echo "${FILE}:${START}: \"aui:${TAG}\" does not pass the getter of \"${PROPERTY}\" of \"${MODEL}\", add valueFunction=\"<%= ${MODEL}::${GETTER} %>\""

					UNMATCHED=

					break
				fi

				if [[ -n ${SOURCES} ]]
				then
					UNMATCHED=${MODEL}
				fi
			done

			if [[ -n ${LIST_TYPE} && -n ${UNMATCHED} ]]
			then
				echo "${FILE}:${START}: \"${UNMATCHED}\" has no \"${GETTER}\" for the list type of the \"aui:select\", set \"listTypeFieldName\" to the list type property of the bean"
			fi
		done
	done
)

printf '%s' "${FINDINGS}"
```

**Judge this from `${FINDINGS}` and never from the exit status**, which carries no verdict in either direction. The loop's status is whatever its last iteration happened to leave behind, so it reports on the diff's last file rather than on the diff.

Run the loop from `${REPO_ROOT}`. The changed paths are relative to the repository root, so from any directory below it, every file test fails and the check passes without scanning anything.

Use `while read` rather than a `for` over an unquoted substitution, so a path holding whitespace stays one file rather than splitting into several.

When `${FINDINGS}` is empty, the branch adds no `aui:input` or `aui:select` that needs a getter it does not pass, so the validation passes.

Otherwise, the validation fails. Report the message below to the developer with one row for each finding, giving the path, the line and the attribute as the finding prints them, and return it as the failure note.

```markdown
**An `aui:input` or `aui:select` does not pass the getter of its bean.** Each tag below names a property of its bean, but the tags no longer look the getter up by name, so the field would not show the bean's value. Add the attribute to the tag. Use the declared type of the bean when it differs from the model class, and a lambda with a typed parameter, such as `(AssetCategory assetCategory) -> assetCategory.getTitle()`, when the getter is overloaded. A hidden or time zone `aui:input` with both a `value` and a `valueFunction` reads its `valueFunction` only when its `value` is `null`, so remove one of them and write any fallback into the `value` expression. An `aui:select` with a `listType` shows the list type of its bean, so point its `listTypeFieldName` at a property that the bean has a getter for.

| File | Line | Attribute |
| --- | --- | --- |
| <file> | <line> | <attribute> |
```

## Time Estimate

~10-30 sec (static, no build).