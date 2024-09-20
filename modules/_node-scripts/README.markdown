# Liferay `node-scripts`

This document exists to contain an assortment of knowledge about `node-scripts`.

## `node-scripts test`

### Setting up Jest debugging in VS Code [LPD-37078](https://liferay.atlassian.net/browse/LPD-37078)

Add a launch configuration for Jest in VS Code. Quickly do this by opening command palette and searching for `Debug: Add configuration...`. This will add a local configuration to your vscode workspace and should not be committed to git.

Then add a new configuration with the following settings:

```json
{
	"version": "0.2.0",
	"configurations": [
		{
			"type": "node",
			"request": "launch",
			"name": "Liferay node-scripts test: current file",
			"env": {"NODE_ENV": "test"},
			"runtimeExecutable": "yarn",
			"cwd": "${fileDirname}",
			"args": ["test", "${fileBasenameNoExtension}"],
			"console": "integratedTerminal"
		}
	]
}
```

Now you can navigate to your test file and run the vscode debugger.