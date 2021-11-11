import moment from 'moment';

import config from '../../../config';
import { downloadMockedBackup, uploadBackup } from '../../helpers/api/backup';
import { fetchProjectUid } from '../../helpers/api/project';
import * as file from '../../helpers/file';
import {
  navigateToProjectPage,
  navigateToProjectServicePage,
} from '../../helpers/navigator';
import activitiesPage from '../../pages/activities-page';
import backupsPage from '../../pages/backups-page';
import basePage from '../../pages/base-page';
import loginPage from '../../pages/login-page';
import logsPage from '../../pages/logs-page';
import { setupBackup } from '../setups';

const projectId = `qadxp1${config.remote.split('.')[1]}${
  config.liferayVersion
}`;

const isAntD = true;

fixture('Backup')
  .meta('profile', 'dxpstack_backup')
  .page(`${config.consoleUrl}/projects`)
  .before(async (ctx) => {
    await setupBackup(projectId);

    ctx.tester = config.dxpstackTester;
  });

test('Backup manually', async (testController) => {
  const projectPrdUid = await fetchProjectUid(`${projectId}-prd`);

  await loginPage.login(testController.fixtureCtx.tester.email);

  await navigateToProjectPage(`${projectId}-prd`, 'backups');

  await basePage.clickBtn('Backup Now', { isAntD, timeout: 60000 });

  await basePage.assertToast('Backup started', { isAntD, timeout: 10000 });

  await backupsPage.assertInProgressCard({
    bannerMessage:
      'Warning: a backup creation is underway. All backup features will be available again when the creation is complete.',
    statusMessage: 'Backup creation in progress...',
  });

  await backupsPage.assertInProgressCard({
    bannerMessage:
      'Success: backup creation succeeded. All backup features are available again.',
    statusMessage: 'Backup creation succeeded',
  });

  await basePage.assertToast('Backup succeeded', { isAntD, timeout: 10000 });

  const timeStamp = moment.utc().format('YYYYMMDDHH');
  const createdAt = moment().format('MMM DD YYYY');

  await backupsPage.assertRow(0, {
    name: `dxpcloud-${projectPrdUid}-${timeStamp}`,
    createdAt,
    creationType: 'Manual',
  });

  await navigateToProjectPage(`${projectId}-prd`, 'activities');

  await activitiesPage.assertActivity(`Backup started\n${projectId}-prd`);
  await activitiesPage.assertActivity(`Backup succeeded\n${projectId}-prd`);
});

test
  .before(async (testController) => {
    await downloadMockedBackup('xsmall-db', 'gz');
    await downloadMockedBackup('volume');

    const dirPath = `${config.projectRoot}/${config.dependencies}`;

    await uploadBackup(
      `${projectId}-dev`,
      `${dirPath}/xsmall-db.gz`,
      `${dirPath}/volume.tgz`
    );

    await loginPage.login(testController.fixtureCtx.tester.email);
  })(
    'Browser download starts promptly after initiating download',
    async (testController) => {
      await navigateToProjectPage(`${projectId}-dev`, 'backups');

      await basePage.assertText('Backup Now', { timeout: 60000 });

      await backupsPage.selectMenuItem(0, 'Download');

      await basePage.clickBtn('backup-lfr', { isAntD, timeout: 10000 });

      // Browser download should start within 5 seconds
      await testController.wait(5000);

      // File will exist only if browser download has started
      const pattern = new RegExp('.*crdownload');

      const backupFile = file.findByPattern(config.downloadDir, pattern)[0];

      await testController
        .expect(backupFile)
        .notEql(undefined, 'Download file was not found');

      await navigateToProjectServicePage(`${projectId}-dev`, 'backup', 'logs');

      await testController
        .expect(logsPage.logsText.innerText)
        .contains('Download of volume completed successfully', {
          timeout: 180000,
        })
        .expect(logsPage.logsText.innerText)
        .notContains('ERR_STREAM_PREMATURE_CLOSE');
    }
  )
  .after(async () => {
    file.deleteFile(`${config.dependencies}/xsmall-db.gz`);
    file.deleteFile(`${config.dependencies}/volume.tgz`);
  });
