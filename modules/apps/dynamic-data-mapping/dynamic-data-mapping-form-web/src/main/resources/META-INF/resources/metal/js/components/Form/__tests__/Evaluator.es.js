import WithEvaluator from '../Evaluator.es';
import mockPages from './__mock__/mockPages.es';

const fieldType = 'text';
let component;
let pages;

const EvaluatorComponent = WithEvaluator(
	() => (
		<div>
			{'Liferay'}
		</div>
	)
);
const fieldInstance = {
	fieldName: 'Checkbox',
	value: false
};

describe(
	'Evaluator',
	() => {
		beforeEach(
			() => {
				jest.useFakeTimers();
				fetch.resetMocks();

				pages = mockPages;
			}
		);

		afterEach(
			() => {
				if (component) {
					component.dispose();
				}
			}
		);

		it(
			'should render default markup',
			() => {
				component = new EvaluatorComponent(
					{
						fieldType,
						formContext: {}
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should continue propagating the fieldEdited event',
			() => {
				component = new EvaluatorComponent(
					{
						fieldType,
						formContext: {}
					}
				);
				const event = {
					fieldInstance: {
						...fieldInstance,
						value: true
					}
				};
				const spy = jest.spyOn(component, 'emit');

				fetch.mockResponse(
					JSON.stringify({}),
					{
						status: 200
					}
				);

				jest.runAllTimers();

				component._handleFieldEdited(event);

				expect(spy).toHaveBeenCalledWith('fieldEdited', event);
			}
		);

		it(
			'should continue propagating the fieldEdited event when it is evaluable',
			() => {
				component = new EvaluatorComponent(
					{
						fieldType,
						formContext: {
							pages
						}
					}
				);
				const event = {
					fieldInstance: {
						...fieldInstance,
						evaluable: true,
						value: true
					}
				};
				const spy = jest.spyOn(component, 'emit');

				fetch.mockResponse(
					JSON.stringify(pages),
					{
						status: 200
					}
				);

				component._handleFieldEdited(event);

				jest.runAllTimers();

				expect(spy).toHaveBeenCalledWith('fieldEdited', event);
			}
		);

		it(
			'should update the state of the evaluator\'s pages when it receives a new page as it props',
			() => {
				const newPages = [{
					rows: [
						{
							columns: [
								{
									fields: [
										{
											fieldName: 'radioField',
											label: 'Radio',
											type: 'radio'
										}
									],
									size: 3
								},
								{
									fields: [],
									size: 9
								}
							]
						}
					]
				}];

				component = new EvaluatorComponent(
					{
						fieldType,
						formContext: {
							pages
						}
					}
				);

				jest.runAllTimers();

				jest.useFakeTimers();

				component.willReceiveProps(
					{
						formContext: {
							newVal: {
								pages: newPages
							}
						}
					}
				);

				jest.runAllTimers();

				expect(component.state.pages).toEqual(newPages);
			}
		);

		it(
			'should not update the state of the evaluator\'s pages when it receives any property which is not pages',
			() => {
				component = new EvaluatorComponent(
					{
						fieldType,
						formContext: {
							pages
						}
					}
				);

				jest.runAllTimers();

				jest.useFakeTimers();

				component.willReceiveProps(
					{
						someProperty: true
					}
				);

				jest.runAllTimers();

				expect(component.state.pages).toEqual(pages);
			}
		);
	}
);