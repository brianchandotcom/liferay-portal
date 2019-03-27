const babelJest = require('babel-jest');
const fs = require('fs');

const configString = fs.readFileSync('./.babelrc', 'utf8').toString();
const config = JSON.parse(configString);

module.exports = babelJest.createTransformer(config);