'use strict';

describe(
	'Embed Video Plugin',
	() => {
		let commands = {};

		const video1EmbedProvider = {
			id: 'video1',
			tpl: 'video1 {embedId}',
			type: 'video',
			urlSchemes: [
				'http:\/\/url.com/(\\S*)',
				'http:\/\/url2.com/(\\S*)'
			]
		};

		const video2EmbedProvider = {
			id: 'video2',
			tpl: 'video2 {embedId}',
			type: 'video',
			urlSchemes: [
				'http:\/\/url.com/(\\S*)',
				'http:\/\/url2.com/(\\S*)',
				'http:\/\/url3.com/(\\S*)'
			]
		};

		const genericEmbedProvider = {
			id: 'generic',
			tpl: 'generic {embedId}',
			type: 'unknown',
			urlSchemes: [
				'http:\/\/url.com/(\\S*)'
			]
		};

		const editor = {
			addCommand: jest.fn(
				(commandName, commandDefinition) => {
					commands[commandName] = commandDefinition;
				}
			),
			config: {
				embedProviders: [
					genericEmbedProvider,
					video1EmbedProvider,
					video2EmbedProvider
				]
			},
			filter: {
				addElementCallback: jest.fn()
			},
			fire: jest.fn(),
			insertHtml: jest.fn(),
			on: jest.fn(),
			widgets: {
				add: jest.fn()
			}
		};

		let templateOutput;

		beforeAll(
			() => {
				global.CKEDITOR = {
					plugins: {
						add: jest.fn(
							(plugin, pluginDefinition) => {
								pluginDefinition.init(editor);
							}
						),
						get: jest.fn().mockReturnValue(false)
					},
					template: jest.fn(
						template => {
							templateOutput = {
								output: jest.fn(
									data => {
										return data.content ? data.content : template;
									}
								)
							};

							return templateOutput;
						}
					)
				};

				global.Liferay = {
					Language: {
						get: jest.fn(message => message)
					},
					Util: {
						getLexiconIconTpl: jest.fn(icon => icon)
					}
				};

				// We need to require the plugin after mocking the CKEDITOR and Liferay APIs

				require('../../../src/main/resources/META-INF/resources/js/extras/plugins/embed_url');
			}
		);

		beforeEach(
			() => {
				editor.addCommand.mockReset();
				editor.filter.addElementCallback.mockReset();
				editor.fire.mockReset();
				editor.insertHtml.mockReset();
				editor.on.mockReset();
				editor.widgets.add.mockReset();

				templateOutput.output.mockReset();
			}
		);

		describe(
			'embedUrl command',
			() => {
				it(
					'should have been registered as an editor command',
					() => {
						expect(commands.embedUrl).toBeTruthy();
					}
				);

				it(
					'should fire an error event on the editor when something different than a url is passed',
					() => {
						commands.embedUrl.exec(
							editor,
							{
								url: 'foo'
							}
						);

						expect(editor.fire).toHaveBeenCalledTimes(1);
						expect(editor.fire).toHaveBeenCalledWith('error', 'please-enter-a-valid-url');
					}
				);

				it(
					'should fire an error event on the editor when an unsupported platform url is passed',
					() => {
						commands.embedUrl.exec(
							editor,
							{
								url: 'http://foo.com'
							}
						);

						expect(editor.fire).toHaveBeenCalledTimes(1);
						expect(editor.fire).toHaveBeenCalledWith('error', 'sorry,-this-platform-is-not-supported');
					}
				);

				it(
					'should run all providers if no provider type is specified',
					() => {
						commands.embedUrl.exec(
							editor,
							{
								url: 'http://url.com/embedId'
							}
						);

						expect(editor.insertHtml).toHaveBeenCalledWith(
							expect.stringContaining('generic {embedId}')
						);
					}
				);

				it(
					'should only run providers of a different type than specified',
					() => {
						commands.embedUrl.exec(
							editor,
							{
								type: 'video',
								url: 'http://url.com/embedId'
							}
						);

						expect(editor.insertHtml).toHaveBeenCalledWith(
							expect.stringContaining('video1 {embedId}')
						);
					}
				);

				it(
					'should check all urlSchemes in a single provider',
					() => {
						commands.embedUrl.exec(
							editor,
							{
								type: 'video',
								url: 'http://url2.com/embedId'
							}
						);

						expect(editor.insertHtml).toHaveBeenCalledWith(
							expect.stringContaining('video1 {embedId}')
						);
					}
				);
			}
		);
	}
);