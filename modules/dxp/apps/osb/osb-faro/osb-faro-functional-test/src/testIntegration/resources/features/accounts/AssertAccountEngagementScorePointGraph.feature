@spira_Accounts @Accounts @Profile @team_FARO @priority_3
Feature: Select point on account engagement score graph shows individuals
	As a Business User, I should be able to select a point on the graph and it shows individuals

	Background: [Setup] Navigate to Accounts page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page

	Scenario: Select point on engagement score graph
		Given I click "Hilll, Gottlieb and Dicki" in the table
		And I click the "Activities" tab
		And I click the "Engagement Score" card tab
		When I click row number "1" in the bar graph table
		Then I should see an element exists in the table