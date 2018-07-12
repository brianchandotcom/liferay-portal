import register from '../../../src/main/resources/META-INF/resources/liferay/portlet/register.es';

describe(
	'PortletHub',
	() => {

		beforeEach(
			() => {
				jest.useFakeTimers();
			}
		);

		afterEach(
			() => {
				jest.clearAllTimers();
			}
		);

		describe(
			'action',
			() => {
				const ids = portlet.getIds();
				const onStateChange = jest.fn();
				const portletA = ids[0];
				const portletB = ids[1];

				let hubA;
				let hubB;
				let listenerA;

				beforeEach(
					() => {
						fetchMock(
							[
								portletA
							]
						);

						return Promise.all(
							[
								register(portletA),
								register(portletB)
							]
						).then(
							values => {
								hubA = values[0];

								listenerA = hubA.addEventListener(
									'portlet.onStateChange',
									onStateChange
								);

								hubB = values[1];
							}
						);
					}
				);

				afterEach(
					() => {
						hubA.removeEventListener(listenerA);
						hubA = null;
						hubB = null;
					}
				);

				it(
					'is present in the register return object and is a function',
					() => {
						expect(hubA.action).toBeInstanceOf(Function);
					}
				);

				it(
					'throws a TypeError if too many (>2) arguments are provided',
					() => {
						const element = document.createElement('form');
						const parameters = {};

						return hubA.action(parameters, element, 'param3').catch(
							err => {
								expect(err.name).toEqual('TypeError');
								expect(err.message).toEqual('Invalid argument type. Argument 3 is of type [object String]');
							}
						);
					}
				);

				it(
					'throws a TypeError if a single argument is null',
					() => {
						return hubA.action(null).catch(
							err => {
								expect(err.name).toEqual('TypeError');
								expect(err.message).toEqual('Invalid argument type. Argument 1 is of type [object Null]');
							}
						);
					}
				);

				it(
					'throws a TypeError if the element argument is null',
					() => {
						const parameters = {};

						hubA.action(parameters, null).catch(
							err => {
								expect(err.name).toEqual('TypeError');
								expect(err.message).toEqual('Invalid argument type. Argument 2 is of type [object Null]');
							}
						);
					}
				);

				it(
					'throws a TypeError if action parameters is null',
					() => {
						const element = document.createElement('form');

						hubA.action(null, element).catch(
							err => {
								expect(err.name).toEqual('TypeError');
								expect(err.message).toEqual('Invalid argument type. Argument 1 is of type [object Null]');
							}
						);
					}
				);

				it(
					'throws a TypeError if action parameters is invalid',
					() => {
						const element = document.createElement('form');
						const parameters = {
							a: 'value'
						};

						hubA.action(parameters, element).catch(
							err => {
								expect(err.name).toEqual('TypeError');
								expect(err.message).toEqual('a parameter is not an array');
							}
						);
					}
				);

				it(
					'throws a TypeError if the element argument is invalid',
					() => {
						const element = document.createElement('form');
						const parameters = {};

						hubA.action(parameters, 'Invalid').catch(
							err => {
								expect(err.name).toEqual('TypeError');
								expect(err.message).toEqual('Invalid argument type. Argument 2 is of type [object String]');
							}
						);
					}
				);

				it(
					'throws a TypeError if there are 2 element arguments',
					() => {
						const element = document.createElement('form');
						const parameters = {};

						return hubA.action(element, element).catch(
							err => {
								expect(err.name).toEqual('TypeError');
								expect(err.message).toEqual('Too many [object HTMLFormElement] arguments: [object HTMLFormElement], [object HTMLFormElement]');
							}
						);
					}
				);

				it(
					'throws a TypeError if there are 2 action parameters arguments',
					() => {
						const parameters = {};

						return hubA.action(parameters, parameters).catch(
							err => {
								expect(err.name).toEqual('TypeError');
								expect(err.message).toEqual('Too many parameters arguments');
							}
						);
					}
				);

				it(
					'does not throw if both arguments are valid',
					() => {
						const element = document.createElement('form');
						const parameters = {
							param1: ['paramValue1']
						};

						return hubA.action(parameters, element).then(
							() => {
								jest.runAllTimers();
								expect(onStateChange).toHaveBeenCalled();
							}
						);
					}
				);

				it(
					'throws an AccessDeniedException if action is called twice',
					() => {
						const element = document.createElement('form');
						const parameters = {};

						return Promise.all(
							[
								hubA.action(parameters, element),
								hubA.action(parameters, element)
							]
						).catch(
							err => {
								expect(err.name).toEqual('AccessDeniedException');
								expect(err.message).toEqual('Operation is already in progress');
							}
						);
					}
				);

				it(
					'throws an NotInitializedException if no onStateChange listener is registered.',
					() => {
						fetchMock([portletA]);

						const element = document.createElement('form');
						const parameters = {
							param1: ['paramValue1']
						};

						return hubB.action(parameters, element).catch(
							err => {
								expect(err.name).toEqual('NotInitializedException');
								expect(err.message).toEqual('No onStateChange listener registered for portlet: PortletB');
							}
						);
					}
				);

				it(
					'causes the onStateChange listener to be called and state is as expected',
					() => {
						const element = document.createElement('form');
						const parameters = {};

						return hubA.action(parameters, element).then(
							() => {
								jest.runAllTimers();
								expect(onStateChange).toHaveBeenCalled();
							}
						);
					}
				);
			}
		);

		describe(
			'actions affect multiple portlets',
			() => {
				const onStateChangeA = jest.fn();
				const onStateChangeB = jest.fn();
				const onStateChangeC = jest.fn();
				const onStateChangeD = jest.fn();

				const ids = portlet.getIds();
				const portletA = ids[0];
				const portletB = ids[1];
				const portletC = ids[2];
				const portletD = ids[3];

				let hubA;
				let hubB;
				let hubC;
				let hubD;
				let listenerA;
				let listenerB;
				let listenerC;
				let listenerD;
				let listenerZ;

				beforeEach(
					() => {
						return Promise.all(
							[
								register(portletA),
								register(portletB),
								register(portletC),
								register(portletD)
							]
						).then(
							values => {
								hubA = values[0];

								listenerA = hubA.addEventListener(
									'portlet.onStateChange',
									onStateChangeA
								);

								onStateChangeA.mockClear();

								hubB = values[1];

								listenerB = hubB.addEventListener(
									'portlet.onStateChange',
									onStateChangeB
								);

								onStateChangeB.mockClear();

								hubC = values[2];

								listenerC = hubC.addEventListener(
									'portlet.onStateChange',
									onStateChangeC
								);

								onStateChangeC.mockClear();

								hubD = values[3];

								listenerD = hubD.addEventListener(
									'portlet.onStateChange',
									onStateChangeD
								);

								onStateChangeD.mockClear();
							}
						);
					}
				);

				afterEach(
					() => {
						hubA.removeEventListener(listenerA);
						hubB.removeEventListener(listenerB);
						hubC.removeEventListener(listenerC);
						hubD.removeEventListener(listenerD);
					}
				);

				it(
					'throws an AccessDeniedException if called before previous action completes',
					done => {
						fetchMock([portletA]);

						const element = document.createElement('form');
						const parameters = {};

						onStateChangeA.mockClear();
						onStateChangeB.mockClear();

						const fnA = () => hubA.action(parameters, element);

						const fnB = () => hubB.action(parameters, element).catch(err => err);

						return Promise.all([fnA(), fnB()]).then(
							values => {
								const err = values[1];

								expect(err.message).toEqual('Operation is already in progress');
								expect(err.name).toEqual('AccessDeniedException');
								expect(onStateChangeB).not.toHaveBeenCalled();

								done();
							}
						);
					}
				);

				it(
					'allows actions that update the state of 2 portlets. other portlets are not updated',
					() => {
						fetchMock([portletB, portletC]);

						const element = document.createElement('form');
						const parameters = {};

						return hubB.action(parameters, element).then(
							() => {
								expect(onStateChangeA).not.toHaveBeenCalled();
								expect(onStateChangeD).not.toHaveBeenCalled();
							}
						);
					}
				);
			}
		);
	}
);
