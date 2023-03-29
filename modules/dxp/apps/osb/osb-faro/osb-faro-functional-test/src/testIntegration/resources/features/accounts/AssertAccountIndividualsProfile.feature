@spira_Accounts @Accounts @Profile @team_FARO @priority_3
Feature: Assert account individuals profile
	As a Business User, I should be able to see an individuals profile

	Background: [Setup] Navigate to Accounts page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page

	Scenario: Individuals profile shown
		Given I click "Schneider and Sons" in the table
		And I click the "Individuals" tab
		When I click "Annis Osinski" in the table
		Then I should see the Individual Overview Cards with their details