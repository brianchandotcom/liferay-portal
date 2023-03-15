@spira_Pagination @Pagination @Segments @List @Profile @team_FARO @priority_3
Feature: Paginate Segment Profile in Interests tab
	As a Business User, I should be able to paginate to the second page in Interests tab

	Background: [Setup] Navigate to Segment
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click "everybody" in the table

	Scenario: Paginate to second page in Interests tab
		Given I click the "Interests" tab
		And I set the pagination delta to "5"
		When I go to page "2" in the table
		Then I should be on page "2" in the table
		And I should see an element exists in the table