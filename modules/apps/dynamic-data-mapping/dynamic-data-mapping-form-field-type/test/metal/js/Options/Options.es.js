import Options from 'source/Options/Options.es';

let component;
const spritemap = 'icons.svg';

const valueOptions = {
	en_US: [
		{
			label: 'Option 1',
			value: 'Option1'
		},
		{
			label: 'Option 2',
			value: 'Option2'
		}
	]
};

describe(
	'Options',
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
			'should show the options',
			() => {
				component = new Options(
					{
						spritemap,
						value: valueOptions
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should delete an option when delete button is clicked',
			() => {
				component = new Options(
					{
						spritemap,
						value: valueOptions
					}
				);

				const before = component.items.length;

				component.deleteOption(0);

				expect(component.items.length).toEqual(before - 1);
			}
		);

		it(
			'should allow the user to order the fieldName options by dragging and dropping the options',
			() => {
				component = new Options(
					{
						spritemap,
						value: valueOptions
					}
				);

				const spy = jest.spyOn(component, 'emit');

				jest.runAllTimers();

				component.moveOption(1, 0);

				jest.runAllTimers();

				expect(component).toMatchSnapshot();

				expect(spy).toHaveBeenCalledWith('fieldEdited', expect.anything());
			}
		);
	}
);