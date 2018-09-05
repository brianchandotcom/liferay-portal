import Sidebar from '../Sidebar.es';

let component;
const spritemap = 'icons.svg';

const fieldTypes = [
	{
		description: 'Select date from a Datepicker.',
		icon: 'calendar',
		label: 'Date',
		name: 'date'
	},
	{
		description: 'Single line or multiline text area.',
		icon: 'text',
		label: 'Text Field',
		name: 'text'
	},
	{
		description: 'Select only one item with a radio button.',
		icon: 'radio-button',
		label: 'Single Selection',
		name: 'radio'
	},
	{
		description: 'Choose an or more options from a list.',
		icon: 'list',
		label: 'Select from list',
		name: 'select'
	},
	{
		description: 'Select options from a matrix.',
		icon: 'grid',
		label: 'Grid',
		name: 'grid'
	},
	{
		description: 'Select multiple options using a checkbox.',
		icon: 'select-from-list',
		label: 'Multiple Selection',
		name: 'checkbox'
	}
];

describe(
	'Sidebar',
	() => {
		beforeEach(() => jest.useFakeTimers());

		afterEach(
			() => {
				if (component) {
					component.dispose();
				}
			}
		);

		it(
			'should render the default markup',
			() => {
				component = new Sidebar(
					{
						fieldTypes,
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render a Sidebar open',
			() => {
				component = new Sidebar(
					{
						fieldTypes,
						spritemap
					}
				);
				component.open();

				jest.runAllTimers();

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render a Sidebar closed',
			() => {
				component = new Sidebar(
					{
						fieldTypes,
						spritemap
					}
				);

				component.open();
				component.close();

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render a Sidebar with fieldTypes',
			() => {
				component = new Sidebar(
					{
						fieldTypes,
						spritemap
					}
				);

				component.open();

				jest.runAllTimers();

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render a Sidebar with spritemap',
			() => {
				component = new Sidebar(
					{
						fieldTypes,
						spritemap
					}
				);

				component.open();

				jest.runAllTimers();

				expect(component).toMatchSnapshot();
			}
		);

		describe(
			'Interaction with markup',
			() => {
				it(
					'should close Sidebar when click the button close',
					() => {
						component = new Sidebar(
							{
								fieldTypes,
								spritemap
							}
						);

						component.open();

						expect(component.state.open).toBeTruthy();

						const spy = jest.spyOn(component, 'close');
						const {close} = component.refs;

						close.click();

						jest.runAllTimers();

						expect(component.state.open).toBeFalsy();
						expect(spy).toHaveBeenCalled();
					}
				);
			}
		);
	}
);