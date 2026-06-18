---

paths:
  - ".claude/**/*.sh"

---

# Shell Scripts

Shell scripts under `.claude` must run on both Linux, where the GNU coreutils supply long-form flags, and macOS, where the BSD userland does not. Follow the bibline `ai-rules/coding/shell-formatting.md` and `ai-rules/coding/general.md` conventions, with the portability relaxations below. Every other bibline shell rule still applies in full.

## Short Flag Exemptions

The bibline "Long-Form CLI Flags" rule requires long-form, alphabetically sorted flags. Relax it only for tools whose long forms are not portable to the BSD utilities on macOS. For these tools, use the short form on both platforms so a single invocation works everywhere:

- `mkdir -p` — BSD `mkdir` has no `--parents`.
- `pkill -KILL -f` — BSD `pkill` has no `--full` or `--signal=`, and it requires the signal as the first argument, so `-KILL` precedes `-f`.
- `rm -f` — BSD `rm` has no `--force`.
- `sed -i`, `sed -e`, `sed -E` — BSD `sed` has no `--in-place`, `--expression`, or `--regexp-extended`.

Keep long-form flags for every tool not on this list. When a flag has a portable long form, prefer it.

## The `_sed` Wrapper

Do not call `sed` directly. Call the `_sed` wrapper in `_common.sh`, which translates the GNU long-form flags to their BSD equivalents when `uname` reports `Darwin`. Author each call in long form so it reads like any other command, with the command alone on the first line and each flag on its own tab-indented line:

```bash
_sed \
	--in-place \
	--regexp-extended \
	--expression "s/foo/bar/" \
	"${file}"
```

The wrapper maps `--expression` to `-e`, `--in-place` to `-i ""`, and `--regexp-extended` to `-E` on macOS, and passes the arguments through unchanged on Linux.

## Platform Branching

When a command has no form that works on both platforms, branch on the operating system rather than dropping to a lowest common denominator:

```bash
if [[ $(uname) == Darwin ]]
then
	rsync --archive "${source}/" "${target}/"
else
	cp --archive "${source}/." "${target}"
fi
```