@spira_Individuals @Individuals @team_FARO @priority_4
Feature: Assert Engagement Score time filter is disabled for Individuals
	As an Business User, I should see that the Engagement Score card's time filters are disabled for Individuals

	Background: [Setup] Navigate to Individuals page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Individuals" page
		* I click the "Known Individuals" tab

	Scenario: Assert Engagement Score time filter is disabled for an Individual
		Given I click "Alanna Emard" in the table
		When I click the "Engagement Score" card tab
		Then I should see the time filter is disabled