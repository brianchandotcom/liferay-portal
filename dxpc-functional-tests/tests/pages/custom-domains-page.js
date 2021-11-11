import { Selector, t as testController } from 'testcafe';

import basePage from '../pages/base-page';

class CustomDomainsPage {
  constructor() {
    this.domainNameInput = Selector('.form-control');
    this.infoCard = Selector('.info-card__info');
    this.removeBtn = Selector('.input-matrix-fields-right button.close');
  }

  /**
   * @param   {number} rowIndex 0 based row index.
   * @returns          Selector for custom domain input.
   */
  inputRowSelector(rowIndex) {
    return this.domainNameInput.nth(rowIndex);
  }

  /** Assert input field has disabled attribute. */
  async assertDisabledCustomDomainInput() {
    await testController
      .expect(this.domainNameInput.hasAttribute('disabled'))
      .ok();
  }

  /**
   * Assert domain name exists.
   *
   * @param {string} domain Domain name.
   */
  async assertDomain(domain) {
    const locator = this.domainNameInput.withAttribute('value', domain);

    await testController.expect(locator.exists).ok();
  }

  /**
   * Assert domain name do not exists.
   *
   * @param {string} domain Domain name.
   */
  async assertNoDomain(domain) {
    const locator = this.domainNameInput.withAttribute('value', domain);

    await testController.expect(locator.exists).notOk();
  }

  /**
   * Enters a domain name in row provided.
   *
   * @param {number} rowIndex 0 based row index.
   * @param {string} domain   Domain name.
   */
  async enterDomain(rowIndex, domain) {
    const locator = this.inputRowSelector(rowIndex);

    await testController.expect(locator.exists).ok().typeText(locator, domain, {
      replace: true,
    });
  }

  /**
   * Enters array of domain name(s).
   *
   * @param {string[]} domains Array of domain name(s).
   */
  async enterDomains(domains) {
    for (let i = 0; i < domains.length; i++) {
      const row = i;
      await this.enterDomain(row, domains[i]);
    }
  }

  /**
   * Clicks X button to remove domain.
   *
   * @param {string} domain Domain name.
   */
  async removeRow(domain) {
    const locator = Selector(`input[value='${domain}']`);

    await testController
      .click(locator.parent('div').find('button.close'))
      .click(basePage.button.withText('Update Custom Domains'));
  }
}

export default new CustomDomainsPage();
