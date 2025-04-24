## XMLEchoMessageCheck

Do not use self-closing tag for attribute `message` in `<echo>` tag.

#### Example

Incorrect:

`<echo message="Publishing build scan: ${build.scan.url.output}" />`

Correct:

`<echo>Publishing build scan: ${build.scan.url.output}</echo>`