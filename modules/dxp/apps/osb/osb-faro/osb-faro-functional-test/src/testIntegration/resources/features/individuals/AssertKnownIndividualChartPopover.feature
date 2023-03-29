@spira_Individuals @Individuals @team_FARO @priority_3
Feature: Assert Site Activities Chart Popover follows format
	As a Business User, I should be able to see Site Activities chart popover

	Background: [Setup] Navigate to Sites page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Individuals" page

	Scenario: Sites Activity Chart Popover follows format
		Given I click the "Known Individuals" tab
		And I click "Alanna Emard" in the table
		When I mouse over row "1" in "Individual Activities" card
		Then I should see chart popover formatted YYYY MMM DD
