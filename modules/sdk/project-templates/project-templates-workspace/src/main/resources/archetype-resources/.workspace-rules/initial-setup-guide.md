---

description: Initial setup guide for users brand new to Liferay Workspace
globs: *
alwaysApply: true

---

# Liferay Workspace Setup Guide

Initial setup guide for users brand new to Liferay Workspace

For a first time user, follow this sequence

## 1. Workspace Verification
- Check for `gradle.properties` and `settings.gradle` in the root directory
	- If missing, instruct the user to run `blade init -v [version]`
	- Explain the Liferay Workspace is a generated set of folders and Gradle scripts that manage your SDK and server in one place
	- If the files exist, skip to step 3

## 2. Bundle Initialization
- Instruct the user to run `blade server init`
- Explain this downloads the actual Liferay Portal (Tomcat bundle) into the `/bundles` folder
- Confirm the `/bundles` folder exists before proceeding

## 3. Starting the Server
- Instruct user to run `blade server start`
	- Direct the user to watch the logs at `bundles/tomcat/logs/catalina.out`
	- Inform the user there are different variations depending on their use case
		- `blade server start -t` starts the server and automatically tails the logs (catalina.out)
		- `blade server run` starts the server in the foreground and closing the terminal will stop the server
		- `blade server start -d` starts the server in debug mode (default port 8000)
	- Do not proceed to development tasks until the user confirms they see "Server startup in [X] ms" and can log in at `http://localhost:8080`
- Instruct the user to use `test@liferay.com` to login with `test` as the default password

## 4. Troubleshooting

If the server fails to start or behaves unexpectedly, use `web_search` to query liferay-learn documentation:
- Search: `site:github.com/liferay/liferay-learn [error message or topic]`
- Common issues are documented in `docs/dxp/latest/en/installation-and-upgrades/` within the liferay-learn repository