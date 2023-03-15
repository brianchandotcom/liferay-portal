@spira_Data_Source @Data_Source @DXP @team_FARO @priority_3
Feature: Cancel Creating a DXP Data Source
	As an Business User, I should be able to cancel creating a DXP Data Source

	Background: [Setup] Navigate to the Data Source Page and Click to Add a DXP Data Source
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button

	Scenario: Cancel Creating a DXP Data Source
		Given I click the "Liferay DXP" button
		And I click the "Connect with OAuth" button
		And I type "CancelCreatingDXPDataSource - ${Random.1}" into the "Name" input field
		And I type "DXP_CLIENT_ID" into the "Consumer Key/Client ID" input field
		And I type "DXP_SECRET" into the "Consumer Secret/Client Secret" input field
		When I click the "Cancel" button
		And I click the "Leave Page" button
		And I go to the "Data Source" page
		Then I should not see a "DXP" Data Source named "CancelCreatingDXPDataSource - ${Random.1}"