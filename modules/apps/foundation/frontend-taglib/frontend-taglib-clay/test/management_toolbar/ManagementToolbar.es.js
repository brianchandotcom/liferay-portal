import ManagementToolbar from '../../src/main/resources/META-INF/resources/management_toolbar/ManagementToolbar.es';

describe(
	'ManagementToolbar',
	() => {
		let managementToolbar;
		let searchContainer;
		let searchContainerCallbacks;
		let searchContainerHandle;

		afterEach(() => managementToolbar.dispose());

		beforeAll(
			() => {
				global.Liferay = {
					componentReady: function() {
						return {
							then: jest.fn(cb => cb(searchContainer))
						};
					}
				};
			}
		);

		beforeEach(
			() => {
				searchContainerCallbacks = {};
				searchContainerHandle = {removeListener: jest.fn()};

				searchContainer = {
					fire: jest.fn(
						eventName => searchContainerCallbacks[eventName].apply(this)
					),
					on: jest.fn(
						(eventName, cb) => {
							searchContainerCallbacks[eventName] = jest.fn();
							return searchContainerHandle;
						}
					),
					select: {
						toggleAllRows: jest.fn()
					}
				};

				managementToolbar = new ManagementToolbar(
					{
						spritemap: ''
					}
				);
			}
		);

		it(
			'should listen to the rowToggled event from the registered search container',
			() => {
				searchContainer.fire('rowToggled');

				expect(searchContainerCallbacks.rowToggled).toHaveBeenCalled();
			}
		);

		it(
			'should deselect all searchContainer rows',
			() => {
				managementToolbar._handleDeselectAllClicked();

				expect(searchContainer.select.toggleAllRows).toHaveBeenCalledWith(false);
			}
		);

		it(
			'should select all searchContainer rows',
			() => {
				managementToolbar._handleSelectAllClicked();

				expect(searchContainer.select.toggleAllRows).toHaveBeenCalledWith(true);
			}
		);

		it(
			'should toggle the searchContainer selected rows',
			() => {
				managementToolbar._handleSelectPageCheckboxChanged(
					{
						target: {
							checked: true
						}
					}
				);

				expect(searchContainer.select.toggleAllRows).toHaveBeenCalledWith(true);

				managementToolbar._handleSelectPageCheckboxChanged(
					{
						target: {
							checked: false
						}
					}
				);

				expect(searchContainer.select.toggleAllRows).toHaveBeenCalledWith(false);
			}
		);
	}
);