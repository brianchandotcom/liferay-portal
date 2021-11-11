const profile = process.env.TEST_PROFILE;
const reportName = profile || 'report';

const config = {
  assertionTimeout: 3000,
  disableMultipleWindows: true,
  hostname: 'localhost',
  pageLoadTimeout: 10000,
  reporter: [
    {
      name: 'html',
      output: `test-reports/${reportName}.html`,
    },
    {
      name: 'junit',
      output: `test-reports/${reportName}.xml`,
    },
    {
      name: 'spec',
    },
  ],
  retryTestPages: true,
  selectorTimeout: 10000,
  screenshots: {
    path: 'test-reports/screenshots',
    fullPage: true,
    takeOnFails: true,
  },
  skipJsErrors: true,
  skipUncaughtErrors: true,
};

if (profile && profile != 'custom_filter') {
  config.filter = {
    fixtureMeta: {
      profile: profile,
    },
  };
}

if (process.env.QUARANTINE_MODE) {
  config.quarantineMode = {
    attemptLimit: 3,
    successThreshold: 1,
  };
}

console.log('\nConfig:\n' + JSON.stringify(config, null, '  ') + '\n');

module.exports = config;
