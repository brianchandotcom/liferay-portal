# Poshi Language Support for VS Code

## Features

### Go To Definition

Navigate to definitions using `F12` or using the **Go to Definition** command.

#### Go to function/macro file

![Go to function/macro file](images/gifs/POHSI_VSCODE_GOTODEFINITION_01_CLASS_NAME.gif)

#### Go to function/macro definition

![Go to function/macro definition](images/gifs/POHSI_VSCODE_GOTODEFINITION_02_METHOD_NAME.gif)

#### Go to path file

![Go to path file](images/gifs/POHSI_VSCODE_GOTODEFINITION_03_PATH_NAME.gif)

#### Go to locator definition

![Go to locator definition](images/gifs/POHSI_VSCODE_GOTODEFINITION_04_LOCATOR_NAME.gif)

### Completion

Disabled by default. Can be enabled with the **Poshi > Completion: Enabled** setting.

#### Function/Macro completion

![Function/Macro completion](images/gifs/POHSI_VSCODE_COMPLETION_01_METHOD.gif)

## Commands

### Run test case under cursor

Run the current testcase under the cursor. If inside the test case body, the extension will check each line above the cursor and run the first test case name it finds. It can be invoked via the Command Palette or the Context Menu under the command:

```
Poshi: Run test case under cursor
```

#### Run from the command palette

![Run from the command palette](images/gifs/POHSI_VSCODE_COMMAND_RUN_TEST_CASE_UNDER_CURSOR_01_COMMAND_PALETTE.gif)

#### Run from the context menu

![Run from the context menu](images/gifs/POHSI_VSCODE_COMMAND_RUN_TEST_CASE_UNDER_CURSOR_02_CONTEXT_MENU.gif)

### Run test case in file

Choose a test case in the current file and run it. It can be invoked via the Command Palette under the command:

```
Poshi: Run test case in file
```

![Run test case in file](images/gifs/POHSI_VSCODE_COMMAND_RUN_TEST_CASE_IN_FILE_01_COMMAND_PALETTE.gif)

## Formatting

Source formatting powered by Liferay's Source Formatter.

Disabled by default. Can be enabled with the **Poshi > Source Formatter: Enabled** setting.

A Source Formatter standalone jar from [repository.liferay.com](https://repository-cdn.liferay.com/nexus/content/repositories/liferay-public-releases/com/liferay/com.liferay.source.formatter.standalone/1.0.0/.) is required for this to work. Auto-fetching the jar will be available in the future.

This is a work in progress. If enabled, it is recommended that you disable format on save for now and invoke it manually.

## Extension Settings

```json
"poshi.completion.enabled": {
    "description": "Specifies whether or not to enable auto-completion.",
    "default": false,
    "type": "boolean"
},
"poshi.goToDefinition.enabled": {
    "description": "Specifies whether or not to enable go-to-definition.",
    "default": true,
    "type": "boolean"
},
"poshi.sourceFormatter.enabled": {
    "description": "Specifies whether the to use Liferay's Source Formatter as the formatter for Poshi files.",
    "default": false,
    "type": "boolean"
},
"poshi.sourceFormatter.jarPath": {
    "markdownDescription": "The path to the source-formatter-standalone jar. It will be invoked with `java -jar {}`.\nExample: `file:///Users/me/Documents/source-formatter-standalone.jar`",
    "default": "",
    "type": "string",
    "format": "uri"
}
```