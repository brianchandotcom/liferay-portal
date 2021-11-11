import { Selector, t as testController } from 'testcafe';

import { retryWithRefresh } from '../helpers/utils';
import { ActivitiesPage } from './activities-page';
import basePage from './base-page';

const buildStatus = {
  blue: 'progress',
  green: 'confirmation',
  red: 'warning',
  yellow: 'interrupted',
};

class BuildActivitiesPage extends ActivitiesPage {
  constructor() {
    super();
    this.buildGroupHeader = Selector('.group-header');
    this.moreServicesIcon = Selector('.avatar-number');
  }

  activityStatusSelector(buildId, activity, status) {
    switch (status) {
      case 'failed':
      case 'success':
      case 'warning':
        return this.buildActivitySelector(buildId, activity).find(
          `span.service-avatar-status-${status}`
        );
      case 'none':
        return this.buildActivitySelector(buildId, activity).find(
          'span.activity-avatar-status:empty'
        );
      case 'in progress':
        return this.buildActivitySelector(buildId, activity).find(
          'span.service-avatar-status'
        );
      default:
        throw new Error(`Invalid build activity status ${status}`);
    }
  }

  buildActivitySelector(buildId, activity) {
    return this.buildGroupHeaderSelector(buildId)
      .sibling('li')
      .withText(activity);
  }

  buildGroupHeaderSelector(buildId) {
    return Selector('.group-header .tag-text')
      .withText(buildId)
      .parent('div.group-header');
  }

  buildServiceSelector(buildId, service) {
    return this.buildGroupHeaderSelector(buildId).find(
      `div.segmented-avatar *[data-service-id='${service}']`
    );
  }

  buildStatusSelector(buildId, status) {
    return this.buildGroupHeaderSelector(buildId).find(
      `svg use[href*='badge-${buildStatus[status]}']`
    );
  }

  buildUserAvatarSelector(buildId, userName) {
    return this.buildGroupHeaderSelector(buildId).find(
      `span.avatar[data-title*='${userName}']`
    );
  }

  moreInfoSelector(buildId, activity) {
    return this.buildActivitySelector(buildId, activity).find(
      'svg.more-info-icon'
    );
  }

  serviceStatusSelector(buildId, service, status) {
    switch (status) {
      case 'failed':
      case 'success':
      case 'warning':
        return this.buildServiceSelector(buildId, service).find(
          `span.service-avatar-status-${status}`
        );
      case 'none':
        return this.buildServiceSelector(buildId, service).find(
          'span.service-avatar-status:empty'
        );
      case 'in progress':
        return this.buildServiceSelector(buildId, service).find(
          'span.service-avatar-status'
        );
      default:
        throw new Error(`Invalid build service status ${status}`);
    }
  }

  /**
   * Assert activity exists with given build group id, text, and status.
   *
   * @param {string}   buildId           Build group id.
   * @param {object[]} activities        Array of activities.
   * @param {string}   activities.status Activity status (failed|in
   *     progress|none|success|warning)
   * @param {string}   activities.text   Activity text.
   */
  async assertActivitiesWithStatus(buildId, activities) {
    for (const activity of activities) {
      await retryWithRefresh(100, async () => {
        await testController
          .expect(
            this.activityStatusSelector(buildId, activity.text, activity.status)
              .exists
          )
          .ok();
      });
    }
  }

  /**
   * Assert activity does NOT exist with given build group id, text, and status.
   *
   * @param {string}   buildId           Build group id.
   * @param {object[]} activities        Array of activities.
   * @param {string}   activities.status Activity status (failed|in
   *     progress|none|success|warning)
   * @param {string}   activities.text   Activity text.
   */
  async assertNoActivitiesWithStatus(buildId, activities) {
    for (const activity of activities) {
      await retryWithRefresh(10, async () => {
        await testController
          .expect(
            this.activityStatusSelector(buildId, activity.text, activity.status)
              .exists
          )
          .notOk({ timeout: 20000 });
      });
    }
  }

  /**
   * Assert the number of builds and deployment groups.
   *
   * @param {number} count Number of expected build groups.
   */
  async assertBuildGroupCount(count) {
    await testController.expect(this.buildGroupHeader.count).eql(count);
  }

  /**
   * Assert build group header info.
   *
   * @param {string}   buildId          Build group id.
   * @param {object}   options          Items to assert in the build group header.
   * @param {string}   options.status   Build group status (blue|green|red|yellow).
   * @param {string[]} options.services Array of services in the build.
   * @param {string}   options.user     Name of user creating the build.
   */
  async assertBuildGroupHeader(buildId, options) {
    if (options.services) {
      for (const service of options.services) {
        await testController
          .expect(this.buildServiceSelector(buildId, service).exists)
          .ok();
      }
    }

    if (options.status) {
      await testController
        .expect(this.buildStatusSelector(buildId, options.status).exists)
        .ok({ timeout: 60000 });
    }

    if (options.user) {
      await testController
        .expect(this.buildUserAvatarSelector(buildId, options.user).exists)
        .ok();
    }
  }

  /**
   * Assert tooltip text when hovering over ? icon next to activity.
   *
   * @param {string} buildId  Build group id.
   * @param {string} activity Activity text.
   * @param {string} text     Tooltip text.
   */
  async assertMoreInfo(buildId, activity, text) {
    await testController.hover(this.moreInfoSelector(buildId, activity));
    await basePage.assertText(text);
  }

  /**
   * Assert there are no build activities with given build id.
   *
   * @param {string} buildId Build id.
   */
  async assertNoBuildGroup(buildId) {
    await testController
      .expect(this.buildGroupHeaderSelector(buildId).exists)
      .notOk();
  }

  /**
   * Assert the service status dot in the build group header.
   *
   * @param {string} buildId Build id.
   * @param {string} service Service id.
   * @param {string} status  Service status (failed|in progress|warning|none|success)
   */
  async assertServiceStatus(buildId, service, status) {
    await testController
      .expect(this.serviceStatusSelector(buildId, service, status).exists)
      .ok();
  }
}

export default new BuildActivitiesPage();
