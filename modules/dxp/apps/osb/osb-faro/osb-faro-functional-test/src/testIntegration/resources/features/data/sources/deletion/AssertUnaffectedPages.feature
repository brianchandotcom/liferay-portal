@spira_Data_Source @Data_Source @Deletion @team_FARO @priority_4 @blocked
Feature: Assert Unaffected Pages Do Not Appear During Data Source Deletion
	As an Business User, I should not see unaffected Pages during Data Source deletion

	Background: [Setup] Navigate to the Data Source Page and Click to Add a Data Source
		* I set up the local DXP instance
		* I add an OAuth2 application on the local DXP instance
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I click the "Liferay DXP" button
		* I type "AssertUnaffectedPages - ${Random.1}" into the "Name" input field
		* I type "${NGROK_URL}/" into the "URL" input field
		* I type "${OAUTH2_CLIENT_ID}" into the "Consumer Key/Client ID" input field
		* I type "${OAUTH2_SECRET}" into the "Consumer Secret/Client Secret" input field
		* I click the "Authorize & Save" button
		* I accept the Liferay DXP OAuth2 Popup
		* I should see that "AssertUnaffectedPages - ${Random.1}" was successfully authenticated
		* I click the "Configure Data Source" tab
		* I click the button for Contacts Configuration
		* I click the Sync All toggle switch
		* I click the "Save and Continue" button
		* I click the "Configure" button
		* I go to the "Data Source" page
		* I click "AssertUnaffectedPages - ${Random.1}" in the table
		* I click the "Configure Data Source" tab
		* I click the button for Analytics Configuration
		* I click the select all checkbox in the toolbar
		* I click the "Configure" button
		* I create a dummy page called "${Random.2} Page" on the DXP Site

	Scenario: Assert Unaffected Pages Don't Appear During Data Source Deletion
		Given I go to the "Data Source" page
		And I click "AssertUnaffectedPages - ${Random.1}" in the table
		When I click the "Delete Data Source" button
		And I should see that "2 Pages" will be affected on the Data Source deletion page
		And I click the affected "Pages" on the Data Source deletion page
		Then I should see "${Random.2} Page - Liferay" in a table
		And I should not see a page named "empower holistic ROI" in the table