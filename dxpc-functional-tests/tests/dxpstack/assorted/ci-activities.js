import fs from 'fs';
import path from 'path';

import config from '../../../config';
import { fetchBuilds } from '../../helpers/api/project-builds';
import { waitUntilReady } from '../../helpers/api/service';
import * as file from '../../helpers/file';
import * as git from '../../helpers/git';
import { navigateToProjectPage } from '../../helpers/navigator';
import activitiesPage from '../../pages/activities-page';
import buildActivitiesPage from '../../pages/build-activities-page';
import loginPage from '../../pages/login-page';
import { setupAssorted, setupLocalRepo } from '../setups';

const projectId = `qadxp1${config.remote.split('.')[1]}${
  config.liferayVersion
}`;

fixture('CI Activities')
  .meta('profile', 'dxpstack_assorted')
  .page(`${config.consoleUrl}/projects`)
  .before(async (ctx) => {
    await setupAssorted(projectId);

    ctx.tester = config.dxpstackTester;

    await setupLocalRepo(projectId);

    await waitUntilReady(`${projectId}-infra`, 'ci');
  })
  .after(async () => {
    fs.rmdirSync(`${config.projectRoot}/temp`, { recursive: true });
  });

test
  .before(async (testController) => {
    const latestBuildUid = await fetchBuilds(projectId, {
      limit: 1,
      overEnvironments: true,
    });

    testController.ctx.nextBuildUid = (
      parseInt(latestBuildUid[0].buildGroupUid) + 1
    ).toString();

    git.checkoutBranch(projectId, 'master');

    const backupLcpJson = path.join(
      config.projectRoot,
      'temp',
      projectId,
      'backup',
      'LCP.json'
    );

    file.replace(
      backupLcpJson,
      /liferaycloud\/backup:/,
      'liferaycloud/badimage:'
    );

    git.stageFiles(projectId, ['.']);

    git.commitChanges(projectId, 'Update backup with invalid image');

    git.pushBranch(projectId, 'master');
  })('View failed CI activities', async (testController) => {
    const buildId = testController.ctx.nextBuildUid;

    await loginPage.login(testController.fixtureCtx.tester.email);

    await navigateToProjectPage(`${projectId}-dev`, 'overview');

    await buildActivitiesPage.assertActivitiesWithStatus(buildId, [
      { status: 'failed', text: 'Continuous integration failed' },
      { status: 'failed', text: 'Build failed' },
      { status: 'failed', text: 'Build interrupted' },
      { status: 'none', text: 'Build started' },
      { status: 'none', text: 'Continuous integration started' },
    ]);

    await testController
      .expect(activitiesPage.badge.withText('See details in Jenkins').exists)
      .ok()
      .hover(activitiesPage.badge.withText('See details in Jenkins'))
      .expect(activitiesPage.badge.getAttribute('href'))
      .contains(`/blue/organizations/jenkins/${projectId}/detail/master/`);
  })
  .after(async () => {
    git.checkoutBranch(projectId, 'master');

    const backupLcpJson = path.join(
      config.projectRoot,
      'temp',
      projectId,
      'backup',
      'LCP.json'
    );

    file.replace(
      backupLcpJson,
      /liferaycloud\/badimage:/,
      'liferaycloud/backup:'
    );

    git.stageFiles(projectId, ['.']);

    git.commitChanges(projectId, 'Fix backup image');

    git.pushBranch(projectId, 'master');
  });
