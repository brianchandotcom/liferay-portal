@spira_Individuals @Individuals @team_FARO @priority_3
Feature: Assert Individual Engagement Score Chart Popover format
	As an Business User, I should see that the Engagement Score tab chart popover follows format for Individuals

	Background: [Setup] Navigate to Individuals page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Individuals" page

	Scenario: Assert Individual Engagement Score Chart Popover format
		Given I click the "Known Individuals" tab
		And I click "Abram Bauch" in the table
		And I click the "Engagement Score" card tab
		When I mouse over row "1" in "Individual Activities" card
		Then I should see chart popover formatted YYYY MMM DD
