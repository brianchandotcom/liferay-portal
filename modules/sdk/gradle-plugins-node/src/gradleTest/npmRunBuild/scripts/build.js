const fs = require('fs');
const leftPad = require('left-pad')

fs.writeFileSync('build/resources/main/foo.txt', leftPad('foo', 10, 'X'));