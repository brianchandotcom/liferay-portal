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

	it('should continue to propagate the fieldAdded event', () => {
		component = new Builder({
			spritemap,
		});

		const spy = jest.spyOn(component, 'emit');
		const {sidebar} = component.refs;
		const mockEvent = jest.fn();

		sidebar.emit('fieldAdded', mockEvent);

		expect(spy).toHaveBeenCalled();
		expect(spy).toHaveBeenCalledWith('fieldAdded', expect.anything());
	});

	it('should continue to propagate the fieldEdited event', () => {
		component = new Builder({
			spritemap,
		});

		const spy = jest.spyOn(component, 'emit');
		const {sidebar} = component.refs;
		const mockEvent = jest.fn();

		sidebar.emit('fieldEdited', mockEvent);

		expect(spy).toHaveBeenCalled();
		expect(spy).toHaveBeenCalledWith('fieldEdited', expect.anything());
	});

	it('should continue to propagate the fieldMoved event', () => {
		component = new Builder({
			spritemap,
		});

		const spy = jest.spyOn(component, 'emit');
		const {layoutRenderer} = component.refs;
		const mockEvent = jest.fn();

		layoutRenderer.emit('fieldMoved', mockEvent);

		expect(spy).toHaveBeenCalled();
		expect(spy).toHaveBeenCalledWith('fieldMoved', expect.anything());
	});

	it('should continue to propagate the fieldDeleted event', () => {
		component = new Builder({
			spritemap,
		});

		const spy = jest.spyOn(component, 'emit');
		const {layoutRenderer} = component.refs;
		const mockEvent = jest.fn();

		layoutRenderer.emit('deleteButtonClicked', mockEvent);

		expect(spy).toHaveBeenCalled();
		expect(spy).toHaveBeenCalledWith('fieldDeleted', expect.anything());
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
