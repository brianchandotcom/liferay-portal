@spira_Data_Source @Data_Source @DXP @team_FARO @priority_4
Feature: Assert Liferay Data Source Contact Count Preview
	As an Business User, I should be able to see the correct previewed number of Contacts in to be synced when
	configuring a Liferay DXP Data Source

	Background: [Setup] Add a Liferay DXP Data Source
		* I set up the local DXP instance
		* I add an OAuth2 application on the local DXP instance
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I click the "Liferay DXP" button
		* I click the "Connect with OAuth" button
		* I type "AssertLiferayDataSourceContactCountPreview - ${Random.1}" into the "Name" input field
		* I type "${NGROK_URL}/" into the "URL" input field
		* I type "${OAUTH2_CLIENT_ID}" into the "Consumer Key/Client ID" input field
		* I type "${OAUTH2_SECRET}" into the "Consumer Secret/Client Secret" input field
		* I click the "Authorize & Save" button
		* I accept the Liferay DXP OAuth2 Popup
		* I should see that "AssertLiferayDataSourceContactCountPreview - ${Random.1}" was successfully authenticated

	Scenario: Assert the User Preview Count during Contact Configuration for a Liferay DXP Data Source
		Given I click the "Configure Data Source" tab
		And I click the button for Contacts Configuration
		When I click the Sync All toggle switch
		Then I should see a preview that "1" users will be synced