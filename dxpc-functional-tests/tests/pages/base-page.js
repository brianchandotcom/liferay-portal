import { Selector, t as testController } from 'testcafe';

class BasePage {
  constructor() {
    this.button = Selector('button, .btn').filterVisible();
    this.infoIcon = Selector('svg.more-info-icon');
    this.antDButton = Selector('button.ant-btn, .ant-btn').filterVisible();
    this.antDCell = Selector('.ant-table-cell').filterVisible();
    this.antDCheckbox = Selector(
      '.ant-checkbox-wrapper.ant-checkbox-group-item'
    ).filterVisible();
    this.antDPageTitle = Selector('h1').withAttribute(
      'data-testid',
      'page-title'
    );
    this.btnSquaredDropdown = Selector('button.btn-squared');
    this.errorMessage = Selector('.form-alert');
    this.label = Selector('label');
    this.link = Selector('a');
    this.linkWithUrl = this.link.withAttribute('href');
    this.pageTitle = Selector('.header-title');
    this.pageSubtitle = Selector('.header-subtitle');
    this.placeHolder = Selector('.content-placeholder');
    this.antDPlaceHolder = Selector('.lfr-placeholder__title');
    this.sennaLoadingBar = Selector('.senna-loading');
    this.successMessage = Selector('.form-alert-success');
    this.selectedTab = Selector('.content-tab-selected');
    this.antDSelectedTab = Selector('.ant-tabs-tab-active');
    this.tab = Selector('.content-tab');
    this.antDTab = Selector('.ant-tabs-tab');
    this.toast = Selector('.alert-body');
    this.antDToast = Selector('.ant-notification');
    this.validationMessage = Selector('.help-block span, .help-block');
    this.menuSelect = Selector('.select');
    this.openDropdown = Selector(
      '.dropdown.open, .ant-dropdown-open, .ant-select-dropdown:not(.ant-select-dropdown-hidden)'
    );
    this.dropdownMenuOption = this.menuSelect.find('li');
    this.selectedOption = this.menuSelect.find('li.selected');
    this.tooltipBody = Selector('.tt-body');
  }

  async assertDisabledButton(text, { isAntD } = {}) {
    const button = isAntD ? this.antDButton : this.button;

    await testController
      .expect(button.withText(text).hasAttribute('disabled'))
      .ok();
  }

  async assertEnabledButton(label, { isAntD } = {}) {
    const button = isAntD ? this.antDButton : this.button;

    await testController
      .expect(button.withText(label).hasAttribute('disabled'))
      .notOk({ timeout: 10000 });
  }

  async assertErrorMessage(text) {
    await testController.expect(this.errorMessage.innerText).contains(text);
  }

  async assertLink(link) {
    const linkSelector = this.link.withAttribute('href', link.url);

    await testController.expect(linkSelector.innerText).contains(link.text);
  }

  async assertMenuItems(items) {
    await this.openDropdownList(async () => {
      await testController.click(this.btnSquaredDropdown);
    });

    for (const item of items) {
      await testController
        .expect(Selector('.dropdown-menu li').withText(item).exists)
        .ok();
    }
  }

  async assertNoMenuItems(items) {
    await this.openDropdownList(async () => {
      await testController.click(this.btnSquaredDropdown);
    });

    for (const item of items) {
      await testController
        .expect(Selector('.dropdown-menu li').withText(item).exists)
        .notOk();
    }
  }

  async assertNoText(text) {
    await testController
      .expect(Selector('html body').withText(text).exists)
      .notOk(
        `Expected '${text}' not to be present on the page, but found it on the page`
      );
  }

  async assertText(text, { timeout = 3000 } = {}) {
    await testController
      .expect(Selector('html body').withText(text).exists)
      .ok(
        `Expected '${text}' to be present on the page but could not find it`,
        { timeout }
      );
  }

  /**
   * @param {string}  text              Text to be asserted in the page title.
   * @param {object}  [options]         Optional parameters.
   * @param {number}  [options.timeout] Time to wait before failing the page title
   *     assertion; Default is `3000`
   * @param {boolean} [options.isAntD]  If is a new-console page.
   */
  async assertPageTitle(text, { timeout = 3000, isAntD } = {}) {
    const pageTitle = isAntD ? this.antDPageTitle : this.pageTitle;

    await testController
      .expect(pageTitle.withText(text).exists)
      .ok(
        `Expected '${text}' to be present on the page title but could not find it`,
        { timeout }
      );
  }

  /**
   * @param {string}  text             Placeholder text to be asserted.
   * @param {object}  [options]        Optional parameters.
   * @param {boolean} [options.isAntD] If element is on a React page.
   */
  async assertPlaceholderText(text, { isAntD = false } = {}) {
    const placeholderSelector = isAntD
      ? this.antDPlaceHolder
      : this.placeHolder;

    await testController.expect(placeholderSelector.innerText).contains(text);
  }

  /**
   * @param {toast}   text                 Text to be asserted in toast.
   * @param {object}  [options]            Optional parameters.
   * @param {boolean} [options.closeToast] Indicates whether to close toast or
   *     not; Default is `false`
   * @param {number}  [options.timeout]    Time to wait before failing toast
   *     assertion; Default is `3000`
   * @param {boolean} [options.isAntD]     If is a new-console page.
   */
  async assertToast(text, { closeToast, timeout = 3000, isAntD } = {}) {
    const toast = isAntD ? this.antDToast : this.toast;

    const toastWithText = toast.withText(text);

    await testController
      .expect(toastWithText.exists)
      .ok(`Could not find toast with message ${JSON.stringify(text)}`, {
        timeout,
      });

    if (closeToast) {
      await testController.click(toastWithText.sibling('button.close'));
    }
  }

  async assertExactText(
    text,
    { timeout = 5000, container = 'html body' } = {}
  ) {
    await testController
      .expect(Selector(container).withExactText(text).exists)
      .ok({ timeout });
  }

  async assertToasts(messages, { isAntD } = {}) {
    for (let message of messages) {
      await this.assertToast(message, { isAntD });
    }
  }

  async assertTooltip(tooltipText) {
    await testController
      .expect(this.tooltipBody.innerText)
      .contains(tooltipText);
  }

  /**
   * @param {string}  text              Text to be asserted in the button.
   * @param {object}  [options]         Optional parameters.
   * @param {number}  [options.timeout] Time to wait before failing the button
   *     assertion; Default is `3000`
   * @param {boolean} [options.isAntD]  If is a new-console page.
   */
  async clickBtn(text, { timeout = 3000, isAntD } = {}) {
    const button = isAntD ? this.antDButton : this.button;

    const buttonWithText = button.withText(text);

    await testController
      .expect(buttonWithText.exists)
      .ok(`Could not find button with label ${text}`, { timeout })
      .click(buttonWithText);
  }

  /**
   * @param {string}  text              Text to be asserted in the checkbox label.
   * @param {object}  [options]         Optional parameters.
   * @param {number}  [options.timeout] Time to wait before failing checkbox label
   *     assertion; Default is `3000`
   * @param {boolean} [options.isAntD]  If is a new-console page.
   */
  async clickCheckbox(text, { timeout = 3000, isAntD } = {}) {
    const checkbox = isAntD ? this.antDCheckbox : this.checkbox;

    const checkboxWithText = checkbox.withText(text);

    await testController
      .expect(checkboxWithText.exists)
      .ok(`Could not find button with label ${text}`, { timeout })
      .click(checkboxWithText);
  }

  /**
   * @param {string[]} texts             Array of texts to be asserted in the
   *     checkboxes' labels.
   * @param {object}   [options]         Optional parameters.
   * @param {number}   [options.timeout] Time to wait before failing checkbox
   *     label assertion; Default is `3000`
   * @param {boolean}  [options.isAntD]  If is a new-console page.
   */
  async clickCheckboxes(texts = [], options) {
    texts.forEach(async (text) => {
      await this.clickCheckbox(text, options);
    });
  }

  async openDropdownList(fn) {
    const tries = 3;

    for (let i = 0; i < tries; i++) {
      try {
        await fn();
        await testController.wait(2000).expect(this.openDropdown.exists).ok();
        return;
      } catch (error) {
        if (tries === i - 1) {
          throw new Error('Dropdown did not open');
        }
      }
    }
  }

  async reloadPage() {
    await testController.eval(() => location.reload());
  }

  async selectMenuItem(item) {
    await testController
      .click(this.btnSquaredDropdown)
      .click(Selector('.dropdown-menu li').withText(item));
  }

  async selectOption(option) {
    await testController
      .click(this.menuSelect)
      .click(this.dropdownMenuOption.withText(option))
      .expect(this.selectedOption.innerText)
      .contains(option);
  }

  async waitForLoadingBar() {
    await testController
      .expect(this.sennaLoadingBar.exists)
      .notOk({ timeout: 12000 });
  }
}

export default new BasePage();
