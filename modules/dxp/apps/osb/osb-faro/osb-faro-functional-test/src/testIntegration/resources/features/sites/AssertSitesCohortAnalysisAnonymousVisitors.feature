@spira_Sites @Sites @team_FARO @priority_3
Feature: Assert Sites Cohort Analysis sort Anonymous Visitors
	As a Business User, I should be able to sort Cohort Analysis by Anonymous Visitors

	Background: [Setup] Navigate to Sites page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page

	Scenario: Sort Cohort Analysis by Anonymous Visitors
		Given I go to the "Sites" page
		When I click the "Anonymous Visitors" dropdown option from the "All Visitors" dropdown selection
		Then I should see "0" total visitors