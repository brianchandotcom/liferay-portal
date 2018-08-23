import PortletConstants from '../../../src/main/resources/META-INF/resources/liferay/portlet/portlet_constants.es';
import RenderState from '../../../src/main/resources/META-INF/resources/liferay/portlet/RenderState.es';
import register from '../../../src/main/resources/META-INF/resources/liferay/portlet/register.es';

describe(
	'PortletHub',
	() => {

		describe(
			'newState',
			() => {
				it(
					'should return a new RenderState object',
					() => {
						expect.assertions(3);

						return register('PortletB')
							.then(
								hub => {
									const renderState = hub.newState(
										{
											parameters: {
												a: [1, 2, 3],
												b: [4]
											},
											portletMode: 'view',
											windowState: 'normal'
										}
									);

									const valuesA = renderState.getValues('a');

									expect(valuesA).toEqual(
										expect.arrayContaining([1, 2, 3])
									);

									const valueB = renderState.getValue('b');

									expect(valueB).toEqual(4);

									expect(renderState.portletMode).toEqual('view');
								}
							);
					}
				);
			}
		);

		describe(
			'newParameters',
			() => {
				it(
					'should return new parameters according to the data passed',
					() => {
						expect.assertions(4);

						return register('PortletC').then(
							hub => {
								const parameters1 = {
									a: [1, 2, 3],
									b: null,
									c: 'foo',
									d: ['four', 'five', 'six']
								};

								const parameters2 = hub.newParameters(parameters1);

								expect(parameters2.a).toEqual(
									expect.arrayContaining([1, 2, 3])
								);

								expect(parameters2.b).not.toBeDefined();
								expect(parameters2.c).not.toBeDefined();

								expect(parameters2.d).toEqual(
									expect.arrayContaining(['four', 'five', 'six'])
								);
							}
						);
					}
				);
			}
		);

		describe(
			'RenderState',
			() => {
				describe(
					'constructor',
					() => {
						it(
							'should create a RenderState object according to the data passed to the constructor',
							() => {
								const mockData = {
									parameters: {
										a: [null],
										b: [1, 2, 3],
										c: null,
										d: 2
									},
									portletMode: 'edit',
									windowState: 'maximized'
								};

								const renderState = new RenderState(mockData);

								expect(renderState.parameters.a).toBeDefined();
								expect(renderState.parameters.a).toEqual(
									expect.arrayContaining([null])
								);

								expect(renderState.parameters.b).toBeDefined();
								expect(renderState.parameters.b).toEqual(
									expect.arrayContaining([1, 2, 3])
								);

								expect(renderState.parameters.c).not.toBeDefined();
								expect(renderState.parameters.d).not.toBeDefined();

								expect(renderState.portletMode).toEqual(PortletConstants.EDIT);
							}
						);
					}
				);

				describe(
					'clone',
					() => {
						it(
							'should return a new RenderState instance with the same properties',
							() => {
								const renderState1 = new RenderState(
									{
										parameters:
										{
											a: [1, 2, 3],
											b: 'foo',
											c: ['bar', null]
										},
										portletMode: 'view',
										windowState: 'minimized'
									}
								);

								const renderState2 = renderState1.clone();

								expect(renderState2.parameters.a).toEqual(
									expect.arrayContaining(renderState1.parameters.a)
								);

								expect(renderState1.parameters.b).not.toBeDefined();
								expect(renderState2.parameters.b).toEqual(renderState1.parameters.b);

								expect(renderState1.parameters.c).toEqual(
									expect.arrayContaining(['bar', null])
								);

								expect(renderState2.parameters.c).toEqual(
									expect.arrayContaining(renderState1.parameters.c)
								);

								expect(renderState1.portletMode).toEqual(PortletConstants.VIEW);
								expect(renderState2.portletMode).toEqual(renderState1.portletMode);

								expect(renderState1.windowState).toEqual(PortletConstants.MINIMIZED);
								expect(renderState2.windowState).toEqual(renderState1.windowState);
							}
						);
					}
				);

				describe(
					'getValue',
					() => {
						it(
							'should throw an error if specified parameter is not a string',
							() => {
								const renderState = new RenderState(
									{
										parameters: {
											a: [1, 2, 3]
										},
										portletMode: 'edit',
										windowState: 'normal'
									}
								);

								const testFn = () => {
									renderState.getValue(1);
								};

								expect(testFn).toThrow();
							}
						);

						it(
							'should return a value if specified parameter is undefined and default value is specified',
							() => {
								const renderState = new RenderState();

								const defaultValue = [1, 2, 3];

								const value = renderState.getValue('a', defaultValue);

								expect(value).toEqual(
									expect.arrayContaining(defaultValue)
								);
							}
						);

						it(
							'should return a parameter value if it is defined',
							() => {
								const renderState = new RenderState(
									{
										parameters: {
											a: ['foo']
										},
										portletMode: 'edit',
										windowState: 'normal'
									}
								);

								const value = renderState.getValue('a');

								expect(value).toEqual('foo');
							}
						);
					}
				);

				describe(
					'getValues',
					() => {
						it(
							'should throw an error if the specified parameter is not a string',
							() => {
								const renderState = new RenderState();

								const testFn = () => {
									renderState.getValues(1);
								};

								expect(testFn).toThrow();
							}
						);

						it(
							'should return a value if the specified parameter is undefined and a default value is provided',
							() => {
								const renderState = new RenderState();

								const values = renderState.getValues('foo', 'bar');

								expect(values).toEqual('bar');
							}
						);

						it(
							'should return a parameter\'s value if it is defined',
							() => {
								const renderState = new RenderState(
									{
										parameters: {
											data: ['something', 'here']
										},
										portletMode: 'edit',
										windowState: 'normal'
									}
								);

								const values = renderState.getValues('data');

								expect(values).toEqual(
									expect.arrayContaining(['something', 'here'])
								);
							}
						);
					}
				);

				describe(
					'remove',
					() => {
						it(
							'should throw an error if the speficied parameter is not a string',
							() => {
								const renderState = new RenderState();

								const testFn = () => {
									renderState.remove(1);
								};

								expect(testFn).toThrow();
							}
						);

						it(
							'should not remove a existing parameter',
							() => {
								const renderState = new RenderState(
									{
										parameters: {
											data: [1, 2, 3]
										},
										portletMode: 'edit',
										windowState: 'normal'
									}
								);

								const values = renderState.getValues('data');

								expect(values).toEqual(
									expect.arrayContaining([1, 2, 3])
								);

								renderState.remove('data');

								expect(renderState.parameters.data).not.toBeDefined();
							}
						);
					}
				);

				describe(
					'setValue',
					() => {
						it(
							'should throw an error if `name` is not a string',
							() => {
								const renderState = new RenderState();

								const testFn = () => {
									renderState.setValue(1);
								};

								expect(testFn).toThrow();
							}
						);

						it(
							'should throw an error if `value` is not a string',
							() => {
								const renderState = new RenderState();

								const testFn = () => {
									renderState.setValue('a', 1);
								};

								expect(testFn).toThrow();
							}
						);

						it(
							'should throw an error if `value` is not a array',
							() => {
								const renderState = new RenderState();

								const testFn = () => {
									renderState.setValue(
										'a',
										{
											foo: 'bar'
										}
									);
								};

								expect(testFn).toThrow();
							}
						);

						it(
							'should throw an error if `value` is not null',
							() => {
								const renderState = new RenderState();

								const testFn = () => {
									renderState.setValue('a', undefined);
								};

								expect(testFn).toThrow();
							}
						);

						it(
							'should set a parameter if `value` is a string',
							() => {
								const renderState = new RenderState();

								renderState.setValue('a', 'foo');

								expect(renderState.getValue('a')).toEqual('foo');
							}
						);

						it(
							'should set a parameter if `value` is null',
							() => {
								const renderState = new RenderState();

								renderState.setValue('b', null);

								const value = renderState.getValue('b');

								expect(value).toEqual(null);
							}
						);

						it(
							'should set a parameter if `value` is an array',
							() => {
								const renderState = new RenderState();

								renderState.setValue('c', [4, 5, 6]);

								const value = renderState.getValue('c');

								expect(value).toEqual(4);

								const values = renderState.getValues('c');

								expect(values).toEqual(
									expect.arrayContaining([4, 5, 6])
								);
							}
						);
					}
				);

				describe(
					'setValues',
					() => {
						it(
							'should throw an error if `value` is not a string',
							() => {
								const renderState = new RenderState();

								const testFn = () => {
									renderState.setValues('a', 1);
								};

								expect(testFn).toThrow();
							}
						);

						it(
							'should throw an error if `value` is not null',
							() => {
								const renderState = new RenderState();

								const testFn = () => {
									renderState.setValues('b', undefined);
								};

								expect(testFn).toThrow();
							}
						);

						it(
							'should throw an error if `value` is not an array',
							() => {
								const renderState = new RenderState();

								const testFn = () => {
									renderState.setValues(
										'c',
										{
											foo: 'bar'
										}
									);
								};

								expect(testFn).toThrow();
							}
						);

						it(
							'should set a parameter value if `value` is a string',
							() => {
								const renderState = new RenderState();

								renderState.setValues('data', 'hello');

								const values = renderState.getValues('data');

								expect(values).toEqual(
									expect.arrayContaining(['hello'])
								);

								const value = renderState.getValue('data');

								expect(value).toEqual('hello');
							}
						);

						it(
							'should set a parameter value if `value` is null',
							() => {
								const renderState = new RenderState();

								renderState.setValues('data', null);

								const values = renderState.getValues('data');

								expect(values).toEqual(
									expect.arrayContaining([null])
								);

								const value = renderState.getValue('data');

								expect(value).toEqual(null);
							}
						);

						it(
							'should set a parameter value if `value` is an array',
							() => {
								const renderState = new RenderState();

								renderState.setValues(
									'url',
									['one', 'two', 'three']
								);

								const values = renderState.getValues('url');

								expect(values).toEqual(
									expect.arrayContaining(['one', 'two', 'three'])
								);

								const value = renderState.getValue('url');

								expect(value).toEqual('one');
							}
						);
					}
				);
			}
		);
	}
);