@spira_Data_Source @Data_Source @DXP @team_FARO @priority_4
Feature: Invalid OAuth Credentials
	As an Business User, I should see the expected error message when I enter invalid OAuth2 Consumer Key/Client ID
	while attempting to authorize a Liferay DXP Data Source

	Background: [Setup] Navigate to the Data Source page
		* I set up the local DXP instance
		* I add an OAuth2 application on the local DXP instance
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I click the "Liferay DXP" button
		* I click the "Connect with OAuth" button

	Scenario: Invalid OAuth2 Key
		Given I type "InvalidOAuthKey - ${Random.1}" into the "Name" input field
		And I type "${NGROK_URL}/" into the "URL" input field
		And I type "invalid Consumer Key/Client ID" into the "Consumer Key/Client ID" input field
		And I type "${OAUTH2_SECRET}" into the "Consumer Secret/Client Secret" input field
		When I click the "Authorize & Save" button
		And I try to accept the Liferay DXP OAuth2 Popup
		Then I should see an oauth alert message "The application did not provide a valid client ID"
		And I close the focused window
		And I switch to the main window