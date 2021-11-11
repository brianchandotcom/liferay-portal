# DXPC Functional Tests
These are a few sample tests from lcp-functional-tests project. The test files are in `tests/dxpstack` and `tests/platform`.

## Setup

### Install dependencies
1. Clone project repo
2. Install the node version indicated in `.nvmrc`
3. Run `npm install` from project root (`dxpc-functional-tests`)

## Set environment variables

You will need a `.env` file with the environment variables. Please DM Christie Yoo to request this file.

## Run tests

```sh
$ npm run test
```

## Test results

A test report is generated on each test execution and saved to `test-reports/report.html`. Open this file in a browser to view detailed test results, including a screenshot for failed tests.
