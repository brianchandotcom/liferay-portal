@spira_Data_Source @Data_Source @DXP @Token @team_FARO @priority_5 @prototype
Feature: Add a Liferay DXP Data Source Using the Authentication Token
	As an Business User, I should be able to connect a Liferay DXP Data Source using the Authentication Token

	Background: [Setup] Navigate to the Data Source Page and Click to Add a Data Source
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button

	Scenario: Add a Liferay DXP Data Source using the Authentication Token
		Given I click the "Liferay DXP" button
		And I click the "Connect with Token" button
		And I copy the DXP Authentication Token
		And I set up the local DXP instance
		When I connect Analytics Cloud to DXP using the Authentication Token
		And I go to the "Data Source" page
		And I click "Liferay DXP" in the table
		Then I should see that "Liferay DXP" was successfully authenticated