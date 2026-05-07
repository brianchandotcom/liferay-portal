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

The skill runs the dual-mode formatter (auto-applies fixable violations and lists unfixable ones) and then applies the manual rules. Unfixable violations fail pr-check so the developer fixes them manually.

## Auto-Commit

The formatter is dual-mode: it auto-applies fixable violations and lists unfixable ones as errors. When `git status --porcelain` is non-empty after the formatter (fixable subset applied), stage all changes (`git add --all`) and invoke the `commit` skill with the hint `SF`. The commit skill produces `<TICKET> SF`.

When the commit fails, record the failure and continue to the next validation.

Unfixable violations exit non-zero and fail this validation; surface them from the formatter output in the final report. When both fixable and unfixable violations occur in one run, the auto-commit captures the fixable subset; the unfixable subset still blocks the run.

## Notes

Run **after** all drift validations so the formatter sees the post-regen tree.

## Time Estimate

~1-2 min.