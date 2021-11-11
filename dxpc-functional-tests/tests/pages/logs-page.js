import moment from 'moment';
import { Selector, t as testController } from 'testcafe';

class LogsPage {
  constructor() {
    this.typeDropdown = Selector('.dropdown-log-type');
    this.selectedType = this.typeDropdown.find('.instance-name');
    this.serviceDropdown = Selector('.dropdown-log-service');
    this.selectedService = this.serviceDropdown.find('.instance-name');
    this.instancesDropdown = Selector('.dropdown-log-container');
    this.selectedInstance = this.serviceDropdown.find('.instance-name');
    this.dateRangeText = Selector('.logsview-topbar-right .body-text');
    this.datepickerDropdown = Selector('.dropdown-log-datepicker');
    this.selectedDateRange = this.datepickerDropdown.find('.truncate');
    this.selectOption = Selector('li.select-option');
    this.logsText = Selector('.log-content-wrapper');
    this.downloadLogsBtn = Selector('a.btn').withText('Download logs');
  }

  /**
   * Assert Download Logs link.
   *
   * @param {string} path API link path.
   */
  async assertDownloadLogsLink(path) {
    let url = path;
    url += moment().subtract(1, 'days').utc().format('YYYY-MM-DDTHH:mm');

    await testController
      .expect(this.downloadLogsBtn.getAttribute('href'))
      .contains(url);
  }

  async selectDateRange(range) {
    await testController
      .click(this.datepickerDropdown.find('button'))
      .click(this.selectOption.withText(range));
  }

  async selectType(type) {
    await testController
      .click(this.typeDropdown.find('button'))
      .click(this.selectOption.withText(type));
  }

  async selectService(service) {
    await testController
      .click(this.serviceDropdown.find('button'))
      .wait(500)
      .click(this.selectOption.withText(service));
  }

  async selectInstance(instance) {
    await testController
      .click(this.instancesDropdown.find('button'))
      .click(this.selectOption.withText(instance));
  }
}

export default new LogsPage();
