import KeyValue from '../KeyValue.es';
import {dom as MetalTestUtil} from 'metal-dom';

let component;
let spritemap = 'icons.svg';

describe('KeyValue', () =>
    afterEach(() => {
        if (component) {
			component.dispose();
        }
    });

    it('should be not edidable', () => {
        component = new KeyValue({
            editable: false,
            spritemap: spritemap
        });

        expect(component).toMatchSnapshot();
    });

    it('should have a helptext', () => {
        component = new KeyValue({
            helpText: 'Type something',
            spritemap: spritemap
        });

        expect(component).toMatchSnapshot();
    });

    it('should have an id', () => {
        component = new KeyValue({
            id: 'ID',
            spritemap: spritemap
        });

        expect(component).toMatchSnapshot();
    });

    it('should have a label', () => {
        component = new KeyValue({
            label: 'label',
            spritemap: spritemap
        });

        expect(component).toMatchSnapshot();
    });

    it('should have a predefined Value', () => {
        component = new KeyValue({
            placeholder: 'Option 1',
            spritemap: spritemap
        });

        expect(component).toMatchSnapshot();
    });

    it('should not be required', () => {
        component = new KeyValue({
            required: false,
            spritemap: spritemap
        });

        expect(component).toMatchSnapshot();
    });

    it('should render Label if showLabel is true', () => {
        component = new KeyValue({
            label: 'text',
            showLabel: true,
            spritemap: spritemap
        });

        expect(component).toMatchSnapshot();
    });

    it('should have a spritemap', () => {
        component = new KeyValue({
            spritemap: spritemap
        });

        expect(component).toMatchSnapshot();
    });

    it('should have a value', () => {
        component = new KeyValue({
            value: 'value',
            spritemap: spritemap
        });

        expect(component).toMatchSnapshot();
    });

    it('should have a keyValue', () => {
        component = new KeyValue({
            keyValue: 'keyValue',
            spritemap: spritemap
        });

        expect(component).toMatchSnapshot();
    });

    it('should remove spaces and convert first letter after space to upper case on change label value', () => {
        component = new KeyValue({
            spritemap: spritemap,
            key: 'keyValue'
        });

        const keyValue = component._formatInput('tes te_A_a')

        expect(keyValue).toEqual('tesTeAa');
    });

    it('should now allow generating field name after editing field name input', () => {
        component = new KeyValue({
            spritemap: spritemap,
            generateName: true

        });

        MetalTestUtil.triggerEvent(component.element.querySelector('input.key-value-input'), 'input', {value: 'foo'});

        expect(component.generateName).toEqual(false);
	});
});