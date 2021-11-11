import dotenv from 'dotenv';
import downloadsFolder from 'downloads-folder';

dotenv.config();

const config = {
  apiBaseUrl: `https://api.${process.env.REMOTE}`,
  consoleUrl: `https://console.${process.env.REMOTE}`,
  downloadDir: downloadsFolder(),
  dxpstackTester: {
    email: process.env.DXPSTACK_TESTER,
    pw: process.env.TESTER_PW,
  },
  githubToken: process.env.GITHUB_TOKEN,
  githubUserEmail: process.env.GITHUB_USER_EMAIL,
  liferayVersion: process.env.LIFERAY_IMAGE
    ? process.env.LIFERAY_IMAGE.substring(0, 3).replace('.', '')
    : '73',
  projectRegion: process.env.PROJECT_REGION || 'us-west1-c1',
  remote: process.env.REMOTE,
  serviceDomain: process.env.SERVICE_DOMAIN,
  tester: {
    pw: process.env.TESTER_PW,
  },
  teamuser: {
    email: process.env.TEAM_USER_EMAIL,
    pw: process.env.TEAM_USER_PW,
  },
  dependencies: 'tests/helpers/dependencies',
  projectRoot: __dirname,
  googleServiceKey: process.env.GOOGLE_SERVICE_KEY,
  oktaLoginAttempts: 10,
};

module.exports = config;
