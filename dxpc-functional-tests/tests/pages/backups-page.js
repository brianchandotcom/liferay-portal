import { Selector, t as testController } from 'testcafe';

import basePage from './base-page';

class BackupRow {
  constructor(rowIndex, { isDr } = {}) {
    let rowSelector = 'tr';
    if (isDr) {
      rowSelector = '.ant-tabs-tabpane-active tr';
    }

    this.row = Selector(rowSelector, { timeout: 30000 }).withAttribute(
      'data-row-key',
      `${rowIndex}`
    );

    this.actionMenu = Selector(
      '.ant-dropdown ul.ant-dropdown-menu'
    ).withAttribute('data-testid', `action-menu-${rowIndex}`);

    this.downloadMenu = Selector('ul.download-menu-card', {
      timeout: 30000,
    }).withAttribute('data-testid', `download-menu-${rowIndex}`);
  }

  itemSelector(column, value) {
    return this.row
      .find('.lfr-table__lfr-table-cell')
      .withAttribute('data-testid', column)
      .withText(value);
  }

  async openActionMenu() {
    await basePage.openDropdownList(async () => {
      await testController.click(this.row.find('button.ant-btn'), {
        timeout: 10000,
      });
    });
  }

  async selectMenuItem(item) {
    await this.openActionMenu();
    await testController.click(this.actionMenu.find('li').withText(item), {
      timeout: 10000,
    });
  }
}

class InProgressCard {
  constructor() {
    this.bannerMessage = Selector('span', { timeout: 30000 }).withAttribute(
      'data-testid',
      'backupInProgressBannerMessage'
    );
    this.statusMessage = Selector('div', {
      timeout: 30000,
    }).withAttribute('data-testid', 'backupInProgressStatusMessage');
  }

  async assertBannerMessage(text) {
    await testController
      .expect(this.bannerMessage.withText(text).exists)
      .ok({ timeout: 180000 });
  }

  async assertStatusMessage(text) {
    await testController
      .expect(this.statusMessage.withText(text).exists)
      .ok({ timeout: 180000 });
  }
}

class BackupsPage {
  constructor() {
    this.inProgressCard = Selector(
      ".ant-card[data-testid='backup-inprogress-card']"
    ).with({ visibilityCheck: true });
    this.rows = Selector('.ant-table-row');
  }
  /**
   * Assert values in a backup row.
   *
   * @param {number}  rowIndex               Index of row.
   * @param {object}  options                Options object.
   * @param {string}  [options.createdAt]    Created at.
   * @param {string}  [options.creationType] Creation type.
   * @param {boolean} [options.isDr]         Whether row is in DR tabpane.
   */
  async assertRow(rowIndex, options) {
    const row = new BackupRow(rowIndex, options);

    for (const column of ['name', 'createdAt', 'creationType']) {
      if (options[column]) {
        const rowItem = row.itemSelector(column, options[column]);
        await testController.expect(rowItem.exists).ok({ timeout: 180000 });
      }
    }
  }

  async assertInProgressCard({ bannerMessage, statusMessage }) {
    const inProgressCard = new InProgressCard();

    await inProgressCard.assertBannerMessage(bannerMessage);
    await inProgressCard.assertStatusMessage(statusMessage);
  }

  async assertDownloadMenuText(rowIndex, text) {
    const row = new BackupRow(rowIndex);
    await testController.expect(row.downloadMenu.withText(text).exists).ok();
  }

  async getNumberOfRows() {
    return this.rows.count;
  }

  /**
   * Select backup row action menu item.
   *
   * @param {number}  rowIndex       Index of row.
   * @param {object}  options        Options object.
   * @param {boolean} [options.isDr] Whether row is in DR tabpane.
   */
  async openActionMenu(rowIndex, options) {
    const row = new BackupRow(rowIndex, options);
    await row.openActionMenu();
  }

  /**
   * Select backup row action menu item.
   *
   * @param {number}  rowIndex       Index of row.
   * @param {string}  item           Value of menu item.
   * @param {object}  options        Options object.
   * @param {boolean} [options.isDr] Whether row is in DR tabpane.
   */
  async selectMenuItem(rowIndex, item, options) {
    const row = new BackupRow(rowIndex, options);
    await row.selectMenuItem(item);
  }
}

export default new BackupsPage();
