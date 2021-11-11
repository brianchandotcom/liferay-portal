import { Selector, t as testController } from 'testcafe';

import config from '../../config';
import { navigateToConsolePage } from '../helpers/navigator';
import { retryWithRefresh } from '../helpers/utils';
import basePage from './base-page';

class LoginPage {
  constructor() {
    this.emailInput = Selector('#email');
    this.emailHelp = Selector('#emailHelp');
    this.passwordInput = Selector('#password');
    this.passwordHelp = Selector('#passwordHelp');
    this.submitButton = Selector('button').withText('Log in');
  }

  /**
   * User log in.
   *
   * @param {string}  email                   User email address.
   * @param {string}  password                User password.
   * @param {Object}  options                 Additional options for logging in.
   * @param {boolean} [options.assertSuccess] Assert successful log in; defaults
   *     to true. Default is `true`
   * @param {boolean} [options.stayLoggedIn]  Flag checkbox for an extended
   *     session when logging in; defaults to true. Default is `true`
   */
  async login(
    email,
    password = config.tester.pw,
    { assertSuccess = true, stayLoggedIn = true } = {}
  ) {
    await navigateToConsolePage('/login');

    await retryWithRefresh(3, async () => {
      await testController
        .typeText(this.emailInput, email)
        .typeText(this.passwordInput, password);

      if (stayLoggedIn) {
        await testController.click(
          basePage.label.withText('Stay logged in on this computer')
        );
      }

      await basePage.clickBtn('Log in');

      if (assertSuccess) {
        await basePage.assertPageTitle('Projects', {
          isAntD: true,
          timeout: 15000,
        });
      }
    });
  }

  async logout() {
    await navigateToConsolePage('/logout');
  }
}

export default new LoginPage();
