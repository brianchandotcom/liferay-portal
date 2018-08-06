import {dom as MetalTestUtil} from 'metal-dom';
import {JSXComponent} from 'metal-jsx';
import createElement from './__mock__/createElement.es';
import Sidebar from '../Sidebar.es';

let component;
let spritemap = 'icons.svg';

const fieldLists = [
    {
        name: 'Date',
        type: 'date',
        icon: 'calendar',
        description: 'Select date from a Datepicker.'
    },
    {
        name: 'Text Field',
        type: 'text',
        icon: 'text',
        description: 'Single line or multiline text area.'
    },
    {
        name: 'Single Selection',
        type: 'radio',
        icon: 'radio-button',
        description: 'Select only one item with a radio button.'
    },
    {
        name: 'Select from list',
        type: 'select',
        icon: 'list',
        description: 'Choose an or more options from a list.'
    },
    {
        name: 'Grid',
        type: 'grid',
        icon: 'grid',
        description: 'Select options from a matrix.'
    },
    {
        name: 'Multiple Selection',
        type: 'checkbox',
        icon: 'select-from-list',
        description: 'Select multiple options using a checkbox.'
    }
];

const fieldContext = [
    {
        rows: [
            {
                columns: [
                    {
                        fields: [
                            {
                                key: 'label',
                                type: 'text',
                                label: 'Label',
                                spritemap,
                            }
                        ],
                        size: 12
                    }
                ]
            }
        ]
    }
];

describe('Sidebar', () => {
    beforeEach(() => {
        jest.useFakeTimers();
    });

    afterEach(() => {
        if (component) {
            component.dispose();
        }
    });

    it('should render the default markup', () => {
        component = new Sidebar({
            spritemap
        });

        expect(component).toMatchSnapshot();
    });

    it('should render a Sidebar open', () => {
        component = new Sidebar({
            spritemap
        });
        component.show();

        jest.runAllTimers();

        expect(component).toMatchSnapshot();
    });

    it('should render a Sidebar closed', () => {
        component = new Sidebar({
            spritemap
        });
        component.show();
        component.close();

        jest.runAllTimers();

        expect(component).toMatchSnapshot();
    });

    it('should render a Sidebar with fieldLists', () => {
        component = new Sidebar({
            spritemap,
            fieldLists,
        });

        expect(component).toMatchSnapshot();
    });

    it('should render a Sidebar with spritemap', () => {
        component = new Sidebar({
            spritemap,
        });

        expect(component).toMatchSnapshot();
    });

    it('should open Sidebar when is edit mode', () => {
        component = new Sidebar({
            fieldFocus: {
                indexColumn: 0,
                indexPage: 0,
                indexRow: 0,
                type: 'text'
            },
            fieldLists,
            fieldContext,
            spritemap
        });

        const spy = jest.spyOn(component, 'show');

        component.props.mode = 'edit';

        jest.runAllTimers();

        expect(component.state.show).toBeTruthy();
        expect(spy).toHaveBeenCalled();
    });

    it('should not update the internal mode with the above mode changes if are not in edit mode', () => {
        component = new Sidebar({
            spritemap,
        });

        const spy = jest.spyOn(component, '_setMode');

        component.props.mode = 'edit';

        jest.runAllTimers();

        expect(component.state.mode).toBe('add');
        expect(spy).toHaveBeenCalled();
    });

    it('should update the internal mode with the above mode changes when in edit mode', () => {
        component = new Sidebar({
            fieldFocus: {
                indexColumn: 0,
                indexPage: 0,
                indexRow: 0,
                type: 'text'
            },
            fieldLists,
            fieldContext,
            spritemap
        });

        const spy = jest.spyOn(component, '_setMode');

        component.props.mode = 'edit';

        jest.runAllTimers();

        expect(component.state.mode).toBe(component.props.mode);
        expect(spy).toHaveBeenCalled();
    });

    it('should reset drag and drop when context changes', () => {
        component = new Sidebar({
            spritemap,
        });

        const spyDrag = jest.spyOn(component._dragAndDrop, 'disposeInternal');
        const spy = jest.spyOn(component, '_startDrag');

        component.props.context = [
            {
                title: 'Untitled page'
            }
        ];

        jest.runAllTimers();

        expect(spy).toHaveBeenCalled();
        expect(spyDrag).toHaveBeenCalled();
    });

    it('should continue to propagate the fieldEdit event', () => {
        component = new Sidebar({
            mode: 'edit',
            fieldFocus: {
                indexColumn: 0,
                indexPage: 0,
                indexRow: 0,
                type: 'text'
            },
            fieldLists,
            fieldContext,
            spritemap
        });
        
        const spy = jest.spyOn(component, 'emit');
        const { layoutRenderer } = component.refs;

        layoutRenderer.emit('fieldEdit', {});

        expect(spy).toHaveBeenCalled();
        expect(spy).toHaveBeenCalledWith(
            'fieldEdit',
            expect.any(Object)
        );
    });

    it('should close sidebar when dragging an item', () => {
        component = new Sidebar({
            fieldLists,
            spritemap
        });

        component.show();

        expect(component.state.show).toBeTruthy();

        component._handleDrag(jest.fn());

        jest.runAllTimers();

        expect(component.state.show).toBeFalsy();
    });

    it('should emit a fieldAdd event when adding in layout', () => {
        component = new Sidebar({
            fieldLists,
            spritemap
        });

        const spy = jest.spyOn(component, 'emit');
        const { field1 } = component.refs;
        const element = createElement({
            tagname: 'div',
            attributes: [
                {
                    key: 'data-ddm-field-column',
                    value: 0
                },
                {
                    key: 'data-ddm-field-row',
                    value: 2
                },
                {
                    key: 'data-ddm-field-page',
                    value: 2
                }
            ]
        });
        const mockEvent = {
            target: {
                parentElement: element,
            },
            source: field1
        };

        component._handleFieldMove(mockEvent, {
            preventDefault: jest.fn()
        });

        jest.runAllTimers();

        expect(spy).toHaveBeenCalled();
        expect(spy).toHaveBeenCalledWith(
            'fieldAdd',
            {
                target: {
                    indexColumn: 0,
                    indexPage: 2,
                    indexRow: 2
                },
                fieldProperties: expect.any(Object),
                data: expect.anything()
            }
        )
    });

    it('should not emit when there is no target to drag item', () => {
        component = new Sidebar({
            fieldLists,
            spritemap
        });

        const spy = jest.spyOn(component, 'emit');
        const mockEvent = {
            target: undefined,
            source: undefined,
        };

        component._handleFieldMove(mockEvent, {
            preventDefault: jest.fn()
        });

        expect(spy).not.toHaveBeenCalled();
    });

    describe('Edit mode', () => {
        it('should not go into edit mode with just fieldContext', () => {
            component = new Sidebar({
                spritemap,
                fieldContext,
            });
    
            expect(component).toMatchSnapshot();
        });
    
        it('should not enter editing mode with only `edit` in mode', () => {
            component = new Sidebar({
                spritemap,
                mode: 'edit'
            });

            expect(component._isEditMode()).toBeFalsy();
            expect(component).toMatchSnapshot();
        });

        it('should go into edit mode with just fieldContext, fieldLists and fieldFocus', () => {
            component = new Sidebar({
                mode: 'edit',
                fieldFocus: {
                    indexColumn: 0,
                    indexPage: 0,
                    indexRow: 0,
                    type: 'text'
                },
                fieldLists,
                fieldContext,
                spritemap
            });
    
            jest.runAllTimers();
    
            expect(component).toMatchSnapshot();
        });
    
        it('should return true when there is fieldFocus, edit mode, fieldLists, and fieldContext', () => {
            component = new Sidebar({
                mode: 'edit',
                fieldFocus: {
                    indexColumn: 0,
                    indexPage: 0,
                    indexRow: 0,
                    type: 'text'
                },
                fieldLists,
                fieldContext,
                spritemap,
            });
    
            expect(component._isEditMode()).toBe(true);
        });

        it('should return false when there is only fieldFocus', () => {
            component = new Sidebar({
                spritemap,
                fieldFocus: {
                    indexColumn: 0,
                    indexPage: 0,
                    indexRow: 0,
                    type: 'text'
                }
            });
    
            expect(component._isEditMode()).toBe(false);
        });
    });

    describe('Interaction with markup', () => {
        it('should the close Sidebar when click outside Sidebar', () => {
            component = new Sidebar({
                spritemap,
            });

            const spy = jest.spyOn(component, '_handleDocClick');

            component.show();

            MetalTestUtil.triggerEvent(document, 'click', {});

            jest.runAllTimers();

            expect(component.state.show).toBeFalsy();
            expect(spy).toHaveBeenCalled();
        });

        it('should not close Sidebar when click inside Sidebar', () => {
            component = new Sidebar({
                spritemap,
            });

            const spy = jest.spyOn(component, '_handleDocClick');

            component.show();

            MetalTestUtil.triggerEvent(component.element, 'click', {});

            jest.runAllTimers();

            expect(component.state.show).toBeTruthy();
            expect(spy).toHaveBeenCalled();
        });

        it('should close Sidebar when click the button close', () => {
            component = new Sidebar({
                spritemap,
            });

            component.show();

            expect(component.state.show).toBeTruthy();

            const spy = jest.spyOn(component, 'close');
            const { close } = component.refs;

            close.click();

            jest.runAllTimers();

            expect(component.state.show).toBeFalsy();
            expect(spy).toHaveBeenCalled();
        });

        it('should change the tab on edit mode', () => {
            component = new Sidebar({
                mode: 'edit',
                fieldFocus: {
                    indexColumn: 0,
                    indexPage: 0,
                    indexRow: 0,
                    type: 'text'
                },
                fieldLists,
                fieldContext,
                spritemap,
            });

            const { tab1 } = component.refs;

            MetalTestUtil.triggerEvent(tab1, 'click', {});

            jest.runAllTimers();

            expect(component.state.tabActive).toBe(1);
        });

        it('should return to add mode', () => {
            component = new Sidebar({
                mode: 'edit',
                fieldFocus: {
                    indexColumn: 0,
                    indexPage: 0,
                    indexRow: 0,
                    type: 'text'
                },
                fieldLists,
                fieldContext,
                spritemap,
            });

            const { previousButton } = component.refs;

            MetalTestUtil.triggerEvent(previousButton.element, 'click', {});

            jest.runAllTimers();

            expect(component.state.mode).toBe('add');
        });
    });
});