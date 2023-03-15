@spira_Data_Source @Data_Source @DXP @team_FARO @priority_4 @prototype
Feature: Preview the number of User Group contacts to sync
	As an Business User, I should be able to see the preview number of User Group contacts to sync

	Background: [Setup] Navigate to the Data Source Page and Click to Add a DXP Data Source
		* I set up the local DXP instance
		* I add an OAuth2 application on the local DXP instance
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I click the "Liferay DXP" button
		* I click the "Connect with OAuth" button
		* I type "UserGroupContactSyncPreview - ${Random.1}" into the "Name" input field
		* I type "${NGROK_URL}/" into the "URL" input field
		* I type "${OAUTH2_CLIENT_ID}" into the "Consumer Key/Client ID" input field
		* I type "${OAUTH2_SECRET}" into the "Consumer Secret/Client Secret" input field
		* I click the "Authorize & Save" button
		* I accept the Liferay DXP OAuth2 Popup
		* I should see that "UserGroupContactSyncPreview - ${Random.1}" was successfully authenticated

	Scenario: Preview the number of User Group contacts to sync
		Given I click the "Configure Data Source" tab
		And I click the button for Contacts Configuration
		When I sync the Data Source Contacts by the following User Groups:
			| Irvine |
		Then I should see a preview that "4" users will be synced