import { Selector, t as testController } from 'testcafe';

export class ActivitiesPage {
  constructor() {
    this.activityList = Selector('.activity-list');
    this.badge = Selector('.activity-badge');
    this.message = Selector('.item-label');
    this.tooltip = Selector('.tooltip-title');
  }

  activityCardSelector(card) {
    return Selector('h3.body-title').withText(card).parent('div.content-body');
  }

  async assertActivity(message, { timeout = 5000 } = {}) {
    await testController
      .expect(this.message.withText(message).exists)
      .ok(`Could not find activity ${message}`, { timeout });
  }

  async assertActivitiesWithOrder(messages) {
    for (let i = 0; i < messages.length; i++) {
      await testController
        .expect(this.message.nth(i).innerText)
        .contains(messages[i]);
    }
  }

  async assertDeploymentActivitiesWithOrder(messages, { timeout = 5000 } = {}) {
    for (let i = 0; i < messages.length; i++) {
      await testController
        .expect(
          this.activityCardSelector('Builds and Deployments')
            .find('.item-label')
            .nth(i).innerText
        )
        .contains(messages[i], { timeout });
    }
  }

  async assertGeneralActivitiesWithOrder(messages, { timeout = 5000 } = {}) {
    for (let i = 0; i < messages.length; i++) {
      await testController
        .expect(
          this.activityCardSelector('General').find('.item-label').nth(i)
            .innerText
        )
        .contains(messages[i], { timeout });
    }
  }

  async clickLinkInActivity(message, link) {
    await testController.click(
      this.message.withText(message).find('a').withText(link)
    );
  }
}

export default new ActivitiesPage();
