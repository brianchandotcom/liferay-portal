const fs = require('fs');
const leftPad = require('left-pad');

fs.writeFileSync('build/foo.txt', leftPad('foo', 10, 'X'));
