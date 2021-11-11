import config from '../../../config';
import {
  createTestProject,
  deleteProject,
  teardownProjects,
} from '../../helpers/api/project';
import {
  deployHosting,
  replaceCustomDomains,
  waitUntilReady,
} from '../../helpers/api/service';
import { createTestUser, deleteUser } from '../../helpers/api/user';
import { navigateToProjectServicePage } from '../../helpers/navigator';
import basePage from '../../pages/base-page';
import customDomainsPage from '../../pages/custom-domains-page';
import loginPage from '../../pages/login-page';

fixture('Quotas')
  .meta('profile', 'platform_3')
  .page(`${config.consoleUrl}/login`)
  .before(async (ctx) => {
    ctx.tester = await createTestUser('basic');
  })
  .after(async (ctx) => {
    await teardownProjects(ctx);
    await deleteUser(ctx.tester.id);
  });

test
  .before(async (testController) => {
    testController.ctx.project = await createTestProject();
    testController.ctx.serviceId = 'hosting';

    const {
      project: { projectId },
      serviceId,
    } = testController.ctx;

    await deployHosting(projectId, serviceId);

    await waitUntilReady(projectId, serviceId);

    await loginPage.login(testController.fixtureCtx.tester.email);

    await navigateToProjectServicePage(projectId, serviceId, 'custom-domains');
  })('Cannot exceed custom domains quota', async (testController) => {
    testController.ctx.project2 = await createTestProject();

    const {
      project: { projectId: projectId1 },
      project2: { projectId: projectId2 },
    } = testController.ctx;

    await deployHosting(projectId2, 'hosting2');

    await waitUntilReady(projectId2, 'hosting2');

    await replaceCustomDomains(projectId1, 'hosting', [
      `1${projectId2}.test`,
      `2${projectId2}.test`,
    ]);

    await navigateToProjectServicePage(
      `${projectId2}`,
      'hosting2',
      'custom-domains'
    );

    await customDomainsPage.enterDomains([
      `3${projectId2}.test`,
      `4${projectId2}.test`,
      `5${projectId2}.test`,
      `6${projectId2}.test`,
    ]);

    await basePage.clickBtn('Update Custom Domains');

    await basePage.assertErrorMessage(
      "You've reached your Custom Domain limit"
    );
  })
  .after(async (testController) => {
    await deleteProject(testController.ctx.project.projectId);
    await deleteProject(testController.ctx.project2.projectId);
  });
