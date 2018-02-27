'use strict';

describe(
	'Embed Video Plugin',
	() => {
		let commands = {};

		const editor = {
			addCommand: jest.fn(
				(commandName, commandDefinition) => {
					commands[commandName] = commandDefinition;
				}
			),
			config: {},
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

		const templateOutput = {
			output: jest.fn()
		};

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
						template => templateOutput
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

				require('../../../src/main/resources/META-INF/resources/js/extras/plugins/embed_video');
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
			'embedVideoUrl command',
			() => {
				it(
					'should have been registered as an editor command',
					() => {
						expect(commands.embedVideoUrl).toBeTruthy();
					}
				);

				it(
					'should fire an error event on the editor when something different than a url is passed',
					() => {
						commands.embedVideoUrl.exec(
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
						commands.embedVideoUrl.exec(
							editor,
							{
								url: 'http://foo.com'
							}
						);

						expect(editor.fire).toHaveBeenCalledTimes(1);
						expect(editor.fire).toHaveBeenCalledWith('error', 'sorry,-this-platform-is-not-supported');
					}
				);

				describe(
					'for Facebook',
					() => {
						it(
							'should turn https://www.facebook.com/userId/videos/videoId/ urls into embed objects',
							() => {
								commands.embedVideoUrl.exec(
									editor,
									{
										url: 'https://www.facebook.com/userId/videos/videoId/'
									}
								);

								expect(editor.insertHtml).toHaveBeenCalledTimes(1);
								expect(templateOutput.output).toHaveBeenCalledWith(
									{
										embedId: 'https://www.facebook.com/userId/videos/videoId/'
									}
								);
							}
						);
					}
				);

				describe(
					'for Twitch',
					() => {
						it(
							'should turn https://www.twitch.tv/videos/videoId urls into embed objects',
							() => {
								commands.embedVideoUrl.exec(
									editor,
									{
										url: 'https://www.twitch.tv/videos/videoId'
									}
								);

								expect(editor.insertHtml).toHaveBeenCalledTimes(1);
								expect(templateOutput.output).toHaveBeenCalledWith(
									{
										embedId: 'videoId'
									}
								);
							}
						);
					}
				);

				describe(
					'for Vimeo',
					() => {
						it(
							'should turn https://vimeo.com/videoId urls into embed objects',
							() => {
								commands.embedVideoUrl.exec(
									editor,
									{
										url: 'https://vimeo.com/videoId'
									}
								);

								expect(editor.insertHtml).toHaveBeenCalledTimes(1);
								expect(templateOutput.output).toHaveBeenCalledWith(
									{
										embedId: 'videoId'
									}
								);
							}
						);

						it(
							'should turn https://vimeo.com/album/albumId/video/videoId urls into embed objects',
							() => {
								commands.embedVideoUrl.exec(
									editor,
									{
										url: 'https://vimeo.com/album/albumId/video/videoId'
									}
								);

								expect(editor.insertHtml).toHaveBeenCalledTimes(1);
								expect(templateOutput.output).toHaveBeenCalledWith(
									{
										embedId: 'videoId'
									}
								);
							}
						);

						it(
							'should turn https://vimeo.com/channels/channelId/videoId urls into embed objects',
							() => {
								commands.embedVideoUrl.exec(
									editor,
									{
										url: 'https://vimeo.com/channels/channelId/videoId'
									}
								);

								expect(editor.insertHtml).toHaveBeenCalledTimes(1);
								expect(templateOutput.output).toHaveBeenCalledWith(
									{
										embedId: 'videoId'
									}
								);
							}
						);

						it(
							'should turn https://vimeo.com/groups/groupId/videos/videoId urls into embed objects',
							() => {
								commands.embedVideoUrl.exec(
									editor,
									{
										url: 'https://vimeo.com/groups/groupId/videos/videoId'
									}
								);

								expect(editor.insertHtml).toHaveBeenCalledTimes(1);
								expect(templateOutput.output).toHaveBeenCalledWith(
									{
										embedId: 'videoId'
									}
								);
							}
						);
					}
				);

				describe(
					'for Youtube',
					() => {
						it(
							'should turn https://www.youtube.com/watch?v=videoId urls into embed objects',
							() => {
								commands.embedVideoUrl.exec(
									editor,
									{
										url: 'https://www.youtube.com/watch?v=videoId'
									}
								);

								expect(editor.insertHtml).toHaveBeenCalledTimes(1);
								expect(templateOutput.output).toHaveBeenCalledWith(
									{
										embedId: 'videoId'
									}
								);
							}
						);
					}
				);
			}
		);
	}
);