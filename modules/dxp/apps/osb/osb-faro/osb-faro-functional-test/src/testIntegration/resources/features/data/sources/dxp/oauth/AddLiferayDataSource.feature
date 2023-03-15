@spira_Data_Source @Data_Source @DXP @team_FARO @priority_5
Feature: Add a Liferay DXP Data Source
	As a Business User, I should be able to connect a Liferay DXP instance as a Data Source

	Background: [Setup] Navigate to the Data Source Page and Click to Add a Data Source
		* I set up the local DXP instance
		* I add an OAuth2 application on the local DXP instance
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button

	Scenario: Add a Liferay DXP Data Source using OAuth2
		Given I click the "Liferay DXP" button
		And I click the "Connect with OAuth" button
		And I type "AddLiferayDataSource - ${Random.1}" into the "Name" input field
		And I type "${NGROK_URL}/" into the "URL" input field
		And I type "${OAUTH2_CLIENT_ID}" into the "Consumer Key/Client ID" input field
		And I type "${OAUTH2_SECRET}" into the "Consumer Secret/Client Secret" input field
		When I click the "Authorize & Save" button
		And I accept the Liferay DXP OAuth2 Popup
		Then I should see that "AddLiferayDataSource - ${Random.1}" was successfully authenticated