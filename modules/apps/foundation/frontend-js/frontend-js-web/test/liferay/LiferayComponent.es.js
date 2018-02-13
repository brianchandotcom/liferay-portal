'use strict';

import fs from 'fs';

describe(
	'LiferayComponent',
	() => {
		describe(
			'Liferay.componentReady',
			() => {
				beforeEach(
					() => {
						const window = {
							Liferay: {
								namespace: 0,
								fire: () => 0
							}
						};

						const AUI = Object.assign(
							() => (
								{
									mix: () => 0,
									namespace: () => 0
								}
							),
							{
								$: Object.assign(
									() => (
										{
											on: () => 0
										}
									),
									{
										ajaxPrefilter: () => 0,
										ajaxSetup: () => 0
									}
								),
								_: {
									assign: () => 0,
									forEach: () => 0
								}
							}
						);

						const themeDisplay = {
							getPathContext: () => 0
						};

						const script = fs.readFileSync(
							'./src/main/resources/META-INF/resources/liferay/liferay.js'
						);

						eval(script.toString());
					}
				);

				it(
					'should return a single component if called before it is registered',
					() => {
						const myButton = {myButton: 'myButton'};

						const promise = Liferay.componentReady('myButton').then(
							component => {
								expect(component).toBe(myButton);
							}
						);

						Liferay.component('myButton', myButton);

						return promise;
					}
				);

				it(
					'should return a single component if called after it is registered',
					() => {
						const myButton = {myButton: 'myButton'};

						Liferay.component('myButton', myButton);

						return Liferay.componentReady('myButton').then(
							component => {
								expect(component).toBe(myButton);
							}
						);
					}
				);

				it(
					'should return an array of components if called before they are registered',
					() => {
						const myButton1 = {myButton1: 'myButton1'};
						const myButton2 = {myButton2: 'myButton2'};

						const promise = Liferay.componentReady('myButton1', 'myButton2').then(
							([component1, component2]) => {
								expect(component1).toBe(myButton1);
								expect(component2).toBe(myButton2);
							}
						);

						Liferay.component('myButton1', myButton1);
						Liferay.component('myButton2', myButton2);

						return promise;
					}
				);

				it(
					'should return an array of components if called after they are registered',
					() => {
						const myButton1 = {myButton1: 'myButton1'};
						const myButton2 = {myButton2: 'myButton2'};

						Liferay.component('myButton1', myButton1);
						Liferay.component('myButton2', myButton2);

						return Liferay.componentReady('myButton1', 'myButton2').then(
							([component1, component2]) => {
								expect(component1).toBe(myButton1);
								expect(component2).toBe(myButton2);
							}
						);
					}
				);

				it(
					'should warn through console when a component is registered twice',
					() => {
						let msg = '';

						console.warn = function() {
							for (let i = 0; i < arguments.length; i++) {
								msg += arguments[i].toString();
								msg += ' ';
							}
						};

						Liferay.component('myButton', 1);
						Liferay.component('myButton', 2);

						expect(msg).toEqual('Component with id "myButton" is being registered twice. This can lead to unexpected behaviour in the "Liferay.component" and "Liferay.componentReady" APIs, as well as in the "*:registered" events. ');
					}
				);
			}
		);
	}
);