import Builder from '../Builder.es';

let component;
let spritemap = 'icons.svg';

describe('Builder', () => {
	beforeEach(() => {
		jest.useFakeTimers();
	});

	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('should render the default markup', () => {
		component = new Builder({
			spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should continue to propagate the fieldAdd event', () => {
		component = new Builder({
			spritemap,
		});

		const spy = jest.spyOn(component, 'emit');
		const {sidebar} = component.refs;
		const mockEvent = jest.fn();

		sidebar.emit('fieldAdd', mockEvent);

		expect(spy).toHaveBeenCalled();
		expect(spy).toHaveBeenCalledWith('fieldAdd', expect.anything());
	});

	it('should continue to propagate the fieldEdit event', () => {
		component = new Builder({
			spritemap,
		});

		const spy = jest.spyOn(component, 'emit');
		const {sidebar} = component.refs;
		const mockEvent = jest.fn();

		sidebar.emit('fieldEdit', mockEvent);

		expect(spy).toHaveBeenCalled();
		expect(spy).toHaveBeenCalledWith('fieldEdit', expect.anything());
	});

	it('should continue to propagate the fieldMove event', () => {
		component = new Builder({
			spritemap,
		});

		const spy = jest.spyOn(component, 'emit');
		const {layoutRenderer} = component.refs;
		const mockEvent = jest.fn();

		layoutRenderer.emit('fieldMove', mockEvent);

		expect(spy).toHaveBeenCalled();
		expect(spy).toHaveBeenCalledWith('fieldMove', expect.anything());
	});

	it('should continue to propagate the fieldDelete event', () => {
		component = new Builder({
			spritemap,
		});

		const spy = jest.spyOn(component, 'emit');
		const {layoutRenderer} = component.refs;
		const mockEvent = jest.fn();

		layoutRenderer.emit('deleteButtonClicked', mockEvent);

		expect(spy).toHaveBeenCalled();
		expect(spy).toHaveBeenCalledWith('fieldDelete', expect.anything());
	});

	it('should continue to propagate the fieldClicked event and open the sidebar', () => {
		component = new Builder({
			spritemap,
		});

		const spy = jest.spyOn(component, 'emit');
		const {layoutRenderer, sidebar} = component.refs;
		const mockEvent = jest.fn();

		layoutRenderer.emit('fieldClicked', mockEvent);

		jest.runAllTimers();

		expect(spy).toHaveBeenCalled();
		expect(spy).toHaveBeenCalledWith('fieldClicked', expect.anything());
		expect(sidebar.state.show).toBeTruthy();
	});

	it('should open the sidebar when click the creation button', () => {
		component = new Builder({
			spritemap,
		});

		const {managementToolbar, sidebar} = component.refs;

		managementToolbar.refs.creationMenu.element.click();

		jest.runAllTimers();

		expect(sidebar.props.mode).toBe('add');
		expect(sidebar.state.mode).toBe('add');
		expect(sidebar.state.show).toBeTruthy();
	});
});
