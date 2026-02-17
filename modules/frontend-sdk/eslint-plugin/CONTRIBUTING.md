# Release process

1.  Confirm that you have a clean worktree.

    `git status` should show no changed, staged, or untracked files.

2.  Run the `yarn ci` script.

    This checks formatting, runs lints checks, and runs all tests.

    If necessary, apply fixes, submit a pull request, and when it is merged, start again at step 1.

3.  Update [CHANGELOG.md](./CHANGELOG.md).

    Run [`@liferay/changelog-generator`](https://www.npmjs.com/package/@liferay/changelog-generator):

        yarn run liferay-changelog-generator --interactive

    This will show a preview of the changes, prompt you to enter a release type (eg. major, minor etc, or a specific version), and then stage the changes for inclusion in the release commit.

    Feel free to edit if you want to make improvements; if you do, remember to stage the changes:

        git add CHANGELOG.md

4.  Tag and publish the new release.

    Run `yarn version --minor` (or `--major`, or `--patch`, using the same release type that you selected in the previous step).

    This will update the package.json and create a tagged commit, including the updates to the CHANGELOG that you previously made.

        We use [@liferay/js-publish](https://github.com/liferay/liferay-frontend-projects/tree/master/projects/npm-tools/packages/js-publish) from the "postversion" script to take care of pushing to the repo, and actually publishing to the NPM registry; just follow the prompts.

5.  Update the release notes.

    Take the new section from the top of the CHANGELOG and add it to [the release page](https://github.com/liferay/liferay-frontend-projects/releases) on GitHub.

6.  Sanity check [the @liferay/eslint-plugin page](https://www.npmjs.com/package/@liferay/eslint-plugin) on npmjs.com

    Specifically, you should see the version you just released under the "Versions" tab on that page.
