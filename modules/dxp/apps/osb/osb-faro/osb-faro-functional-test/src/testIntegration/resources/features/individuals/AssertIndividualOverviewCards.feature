@spira_Individuals @Individuals @Profile @team_FARO @priority_4
Feature: Assert the Individuals Overview Cards
	As an Business User, I should be able to assert the cards on the Overview tab of an Individual's page

	Background: [Setup]
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page

	Scenario: Assert the Individuals Overview Cards
		Given I go to the "Individuals" page
		And I click the "Known Individuals" tab
		And I search for "Willa Watsica"
		When I click "Willa Watsica" in the table
		Then I should see the Individual Overview Cards with their details