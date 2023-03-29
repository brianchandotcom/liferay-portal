@spira_Data_Source @Data_Source @DXP @Token @team_FARO @priority_4 @prototype
Feature: Validate Invalid Token
	As an Business User, I should not be able to sync a DXP Data Source with an invalid Token

	Background: [Setup] Navigate to the Data Source Page and Click to Add a Data Source
		* I set up the local DXP instance

	Scenario: Validate Invalid Token
		Given I go to the "Analytics Cloud" DXP Page
		And I type "INVALID_TOKEN_CONTENT" into the "Analytics Cloud Token" input field
		When I click the "Connect" button
		Then I should see an error alert saying "Error:Your request failed to complete."
		When I go to the "Home" page
		And I login as "test@faro.io:test"
		And I should see the "Sites" page
		And I go to the "Data Source" page
		Then I should not see a "DXP" Data Source named "Liferay DXP"
