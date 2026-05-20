# Source Format

## Trigger

Always.

## Match

`.`

## Command

Run the SDK setup, then invoke the `format-source` skill:

```bash
(cd "${REPO_ROOT}" && ant setup-sdk)
```

The skill runs the dual-mode formatter (automatically applies fixable violations and lists unfixable ones) and then applies the manual rules. Unfixable violations fail pr-check so the developer fixes them manually.

## Autocommit

The formatter is dual-mode: it automatically applies fixable violations and lists unfixable ones as errors. When `git status --porcelain` is nonempty after the formatter (fixable subset applied), stage all changes (`git add --all`) and create a commit titled `<TICKET> SF`.

When the commit fails, record the failure and continue to the next validation.

Unfixable violations exit nonzero and fail this validation; surface them from the formatter output in the final report. When both fixable and unfixable violations occur in one run, the autocommit captures the fixable subset; the unfixable subset still blocks the run.

## Notes

Run **after** all drift validations so the formatter sees the regenerated tree.

## Time Estimate

~1-2 min.